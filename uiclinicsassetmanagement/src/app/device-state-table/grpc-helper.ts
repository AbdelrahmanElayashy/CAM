import {GRPCDefectPriority, GRPCState, StandardRequest, StatesByIdRequest} from '../proto/stateTest_pb';
import {DeviceStateViewRow} from '../model/deviceStateViewRow';
import {StateGRPCService} from '../services/state-grpc.service';
import {HelperService} from '../services/helper.service';
import {StateService} from '../services/state.service';
import {Timestamp} from 'google-protobuf/google/protobuf/timestamp_pb';
import {CookieService} from 'ngx-cookie-service';

export class GrpcHelper {

  constructor(
    private grpcService: StateGRPCService,
    private helperService: HelperService,
    private stateService: StateService,
    private cookieService: CookieService
  ) {
  }

  fillStatesByIdsRequest(deviceIds: string[], userId: string, filterValues: any): StatesByIdRequest {

    const result = new StatesByIdRequest();
    result.setDeviceuuidList(deviceIds);
    result.setUseruuid(userId);

    // Filter füllen für gRPC-Call
    result.setFromdate(this.dateToTimestamp(filterValues.from));
    result.setTodate(this.dateToTimestamp(filterValues.to));

    if (filterValues.priority) {
      const priority = (GRPCDefectPriority)[filterValues.priority];
      result.setDefectpriority(priority);
    } else {
      result.setDefectpriority(null);
    }

    if (filterValues.states) {
      result.clearStateList();
      filterValues.states.forEach(state => {
        result.addState((GRPCState)[state]);
      });
    } else {
      result.clearStateList();
    }

    return result;
  }

  timestampToDateString(timestamp: Timestamp): string {
    if (timestamp) {
      return timestamp.toDate().toString();
    }

    return null;
  }

  dateToTimestamp(date: Date): Timestamp {
    let result: Timestamp;
    if (date) {
      result = new Timestamp();
      result.fromDate(date);
    }
    return result;
  }

  fillDeviceStateView(medDeviceData: any, deviceIds: string[], filterValues: any): Promise<DeviceStateViewRow[]> {

    return new Promise<DeviceStateViewRow[]>((resolve, reject) => {

      const result: DeviceStateViewRow[] = [];
      const userId = this.cookieService.get('userId');

      const request = this.fillStatesByIdsRequest(deviceIds, userId, filterValues);

      this.grpcService.getStatesByIds(request)
        .then((response) => {
          const states = response.getStateList();

          states.forEach(state => {
            const curRow = new DeviceStateViewRow();

            curRow.deviceId = state.getDeviceuuid();
            curRow.name = medDeviceData.filter(devices => devices.deviceId === state.getDeviceuuid())[0].descriptionData.deviceName;

            const priorityName = Object.entries(GRPCDefectPriority).find(([key, value]) => value === state.getDefectpriority())[0];
            curRow.defectPriority = priorityName;
            curRow.state = Object.entries(GRPCState).find(([key, value]) => value === state.getDevicestate())[0];

            if (curRow.state === 'NO_STATE') {
              curRow.addInitialStateButton = true;
              curRow.removeStateButton = false;
            } else {
              curRow.addInitialStateButton = false;
              curRow.removeStateButton = true;
            }

            const statesWithAvailabilityDate = ['DEFECT_EXTERNAL_REPAIR', 'DEFECT_OWN_REPAIR', 'MAINTENANCE'];
            if (statesWithAvailabilityDate.some(x => x === curRow.state)) { // vgl. LINQ-Any()
              curRow.showChangeAvailabilityDate = true;
            } else {
              curRow.showChangeAvailabilityDate = false;
            }

            curRow.availableAt = this.timestampToDateString(state.getAvailabilitydate());
            curRow.enteredAt = this.timestampToDateString(state.getChangetime());

            // getPossibleNextStates abfragen:
            const getPossibleNextStatesRequest = new StandardRequest();
            getPossibleNextStatesRequest.setUseruuid(userId);
            getPossibleNextStatesRequest.setDeviceuuid(curRow.deviceId);

            this.grpcService.getPossibleNextStates(getPossibleNextStatesRequest)
              .then((posNextStatesResponse) => {
                const posNextStates = posNextStatesResponse.getPossiblestateList();

                if (posNextStates && posNextStates.length !== 0) {
                  curRow.showChangeStateButton = true;
                } else {
                  curRow.showChangeStateButton = false;
                }
              });

            result.push(curRow);
          });

          resolve(result);
        });
    });
  }
}
