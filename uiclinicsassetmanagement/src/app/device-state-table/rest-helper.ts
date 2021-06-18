import {DeviceStateViewRow} from '../model/deviceStateViewRow';
import {State} from '../model/state';
import {DialogHelper} from '../services/dialogHelper';
import {InfoDialogComponent} from '../dialog/info-dialog/info-dialog.component';
import {StateGRPCService} from '../services/state-grpc.service';
import {HelperService} from '../services/helper.service';
import {StateService} from '../services/state.service';
import {MatDialog} from '@angular/material/dialog';

export class RestHelper {

  constructor(
    private helperService: HelperService,
    private stateService: StateService,
    private matDialog: MatDialog) {
  }


  fillDeviceStateView(medDeviceData: any, deviceIds: string[], filterValues: any): Promise<DeviceStateViewRow[]> {

    return new Promise((resolve, reject) => {

      const result: DeviceStateViewRow[] = [];

      const param = new Map<string, string>();
      param.set('deviceIds', deviceIds.toString());

      console.log('restHelper:');
      console.log(filterValues);

      this.stateService.postData('getStatesByDeviceIds', null, param, true, filterValues)
        .subscribe(statesData => {

          statesData.forEach(stateInfo => {

            let curObj = new DeviceStateViewRow();
            curObj.deviceId = stateInfo.deviceId;
            curObj.name = medDeviceData.filter(devices => devices.deviceId === stateInfo.deviceId)[0].descriptionData.deviceName;
            curObj.defectPriority = stateInfo.defectPriority;
            curObj.state = stateInfo.state;

            if (stateInfo.state === 'NO_STATE') {
              curObj.addInitialStateButton = true;
              curObj.removeStateButton = false;
            } else {
              curObj.addInitialStateButton = false;
              curObj.removeStateButton = true;
            }

            const statesWithAvailabilityDate = ['DEFECT_EXTERNAL_REPAIR', 'DEFECT_OWN_REPAIR', 'MAINTENANCE'];
            console.log(statesWithAvailabilityDate.some(x => x === stateInfo.state)); // vgl. LINQ-Any()
            if (statesWithAvailabilityDate.some(x => x === stateInfo.state)) {
              curObj.showChangeAvailabilityDate = true;
            } else {
              curObj.showChangeAvailabilityDate = false;
            }

            curObj.availableAt = stateInfo.availabilityDate;

            curObj.enteredAt = stateInfo.enteredAt;

            // Rechte für change State "berechnen" - TODO: evtl auslagern!
            // this.helperService.checkRight(this.helperService.SHOW_CHANGE_STATE_BUTTON, stateInfo.deviceId)
            this.stateService.getData('getPossibleNextStates', stateInfo.deviceId, null, true)
              .subscribe((posNextStates: State[]) => {
                if (posNextStates && posNextStates.length != 0) {
                  curObj.showChangeStateButton = true;

                } else {
                  curObj.showChangeStateButton = false;
                }

                console.log('showChangeStateButton: ' + curObj.showChangeStateButton);
              }, error => {
                // const dialogConfig = DialogHelper.setDefaultErrorDialogConfig(error.message);
                // const modalDialog = this.matDialog.open(InfoDialogComponent, dialogConfig);

                throw error;  // Error erstmal trotzdem werfen
              });

            result.push(curObj);
          });

          resolve(result);
          // this.dataSource.data = resultsToAdd;

        }, error => {
          const dialogConfig = DialogHelper.setDefaultErrorDialogConfig(error.message);
          const modalDialog = this.matDialog.open(InfoDialogComponent, dialogConfig);

          reject(error);
          throw error;  // Error erstmal trotzdem werfen
        });

    });
  }
}
