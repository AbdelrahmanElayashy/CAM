import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {StateService} from '../services/state.service';
import {MatPaginator} from '@angular/material/paginator';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ChangeStateDialogComponent} from '../dialog/change-state-dialog/change-state-dialog.component';
import {State} from '../model/state';
import {MatTableDataSource} from '@angular/material/table';
import {DeviceStateViewRow} from '../model/deviceStateViewRow';
import {MatSort} from '@angular/material/sort';
import {DialogHelper} from '../services/dialogHelper';
import {HelperService} from '../services/helper.service';
import {PriorityChangeDialogComponent} from '../dialog/priority-change-dialog/priority-change-dialog.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {forkJoin} from 'rxjs';
import {MedicalDeviceService} from '../services/medical-device.service';
import {ListDialogComponent} from '../dialog/list-dialog/list-dialog.component';
import {InfoDialogComponent} from '../dialog/info-dialog/info-dialog.component';
import {AddAdditionalInformationDialogComponent} from '../dialog/add-additional-information-dialog/add-additional-information-dialog.component';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {ChangeAvailabilityDateDialogComponent} from '../dialog/change-availability-date-dialog/change-availability-date-dialog.component';
import {MatSelectChange} from '@angular/material/select';
import * as moment from 'moment';
import {StateGRPCService} from '../services/state-grpc.service';
import {StateManagerGRPCService} from '../services/StateManagerGRPCService';
import {
  GRPCDefectPriority,
  GRPCDefectPriorityMap, GRPCState,
  GRPCStateMap,
  InitialStateRequest,
  StandardRequest,
  StateResponse,
  StatesByIdRequest
} from '../proto/stateTest_pb';
import * as google_protobuf_timestamp_pb from 'google-protobuf/google/protobuf/timestamp_pb';
import {GrpcHelper} from './grpc-helper';
import {RestHelper} from './rest-helper';
import {CookieService} from 'ngx-cookie-service';

// import {StateGRPCService} from '../services/state-grpc.service';

interface Priority {
  value: string;
  viewValue: string;
}

interface StateEnum {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-device-state-table',
  templateUrl: './device-state-table.component.html',
  styleUrls: ['./device-state-table.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class DeviceStateTableComponent implements OnInit, AfterViewInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  public dataSource = new MatTableDataSource<DeviceStateViewRow>();
  displayedColumns: string[] = ['name', 'defectPriority', 'state', 'enteredAt', 'enterTime', 'availabilityDate'];

  expandedElement: DeviceStateViewRow | null;

  selectedPrio!: string;
  selectedState!: string[];
  fromDate!: string;
  toDate!: string;

  rights = {  // TODO!
    showChangeStateButton: true
  };

  prios: any[] = [
    {value: 'HIGH', viewValue: 'HIGH'},
    {value: 'MEDIUM', viewValue: 'MEDIUM'},
    {value: 'LOW', viewValue: 'LOW'}
  ];

  states: any[] = [
    {value: 'BOUGHT', viewValue: 'BOUGHT'},
    {value: 'AVAILABLE', viewValue: 'AVAILABLE'},
    {value: 'RESERVED', viewValue: 'RESERVED'},
    {value: 'IN_USE', viewValue: 'IN_USE'},
    {value: 'CLEANING', viewValue: 'CLEANING'},
    {value: 'MAINTENANCE', viewValue: 'MAINTENANCE'},
    {value: 'DEFECT_NO_REPAIR', viewValue: 'DEFECT_NO_REPAIR'},
    {value: 'DISPOSED', viewValue: 'DISPOSED'},
    {value: 'DEFECT_EXTERNAL_REPAIR', viewValue: 'DEFECT_EXTERNAL_REPAIR'},
    {value: 'DEFECT_OWN_REPAIR', viewValue: 'DEFECT_OWN_REPAIR'}
  ];

  // Activate gRPC if available:
  gRPCActive = false;

  grpcHelper: GrpcHelper;
  restHelper: RestHelper;

  constructor(
    private stateService: StateService,
    private medicalDeviceService: MedicalDeviceService,
    private grpcService: StateGRPCService,
    private grpcService1: StateManagerGRPCService,
    public helperService: HelperService,
    private cookieService: CookieService,
    public matDialog: MatDialog) {

    this.grpcHelper = new GrpcHelper(grpcService, helperService, stateService, cookieService);
    this.restHelper = new RestHelper(helperService, stateService, matDialog);
  }

  ngOnInit() {
    this.getAllDevices();
  }

  logit(x) {
    console.log(x);
  }


  public getAllDevices = () => {

    this.medicalDeviceService.getData('medical-devices')
      .subscribe(medDeviceData => {

        const deviceIds = medDeviceData.map(device => device.deviceId);
        const filterValues = {
          states: this.selectedState ? this.selectedState : null,
          priority: this.selectedPrio ? this.selectedPrio : null,
          from: this.fromDate ? this.fromDate : null,
          to: this.toDate ? this.toDate : null
        };

        if (this.gRPCActive) {
          const resultsGRPC = this.grpcHelper.fillDeviceStateView(medDeviceData, deviceIds, filterValues);
          resultsGRPC.then((response) => {
              console.log('Hallo GRPC !!!!');
              this.dataSource.data = response;
            }
          );
        } else {
          const resultsREST = this.restHelper.fillDeviceStateView(medDeviceData, deviceIds, filterValues);
          resultsREST.then((response) => {
              console.log('Hallo REST !!!!');
              this.dataSource.data = response;
            }
          );
        }

        // TEST  gRPC
        // TEST  TEST  TEST  TEST  TEST  TEST  TEST
        // TEST  TEST  TEST  TEST  TEST  TEST  TEST
        // TEST  TEST  TEST  TEST  TEST  TEST  TEST

        // var reqS = new StandardRequest();
        // reqS.setDeviceuuid("b259597f-888b-41f9-ba59-482382be9c23");
        // reqS.setUseruuid("fa527b41-7fea-4718-a48f-c0899f4990a5");
        //
        // let x2: Promise<StateResponse>;
        // x2 = this.grpcService1.state(reqS);
        // x2.then((result) => {
        //
        //   // TODO:
        //   // let nameOfA = GRPCState[result.getDevicestate()];
        //   // console.log(nameOfA);
        //   // let x2 = Object.keys(GRPCState).map(key => GRPCState[key]).filter(value => typeof value === 'string') as string[];
        //   // console.log(x2);
        //
        //   const stateName = Object.entries(GRPCState).find(([key, value]) => value === result.getDevicestate())[0];
        //   console.log(stateName);
        //
        // });
        // console.log(x2);

        // TEST  TEST  TEST  TEST  TEST  TEST  TEST
        // TEST  TEST  TEST  TEST  TEST  TEST  TEST
        // TEST  TEST  TEST  TEST  TEST  TEST  TEST

      }, error => {
        const dialogConfig = DialogHelper.setDefaultErrorDialogConfig(error.message);
        const modalDialog = this.matDialog.open(InfoDialogComponent, dialogConfig);

        throw error;  // Error erstmal trotzdem werfen
      });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  showHistory(deviceId: string) {
    const dialogConfig = DialogHelper.setDefaultListDialogConfig('historyList', 'Bisherige Status',
      this.stateService.restUrl('showHistory', deviceId, null, true)
    );

    const modalDialog = this.matDialog.open(ListDialogComponent, dialogConfig);
  }

  // tslint:disable-next-line:typedef
  openModal(deviceId: string) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.id = 'modal-component';
    dialogConfig.height = '200px';
    dialogConfig.width = '400px';

    // 1. erst Daten des Dialogs laden
    this.stateService.getState(deviceId)
      .subscribe((curState: State) => {

        this.stateService.getData('getPossibleNextStates', deviceId, null, true)
          .subscribe((posNextStates: State[]) => {

            dialogConfig.data = {deviceId, curState, posNextStates};

            // 2. mit geladenen Daten den Dialog öffnen:
            // https://material.angular.io/components/dialog/overview
            const modalDialog = this.matDialog.open(ChangeStateDialogComponent, dialogConfig);


            modalDialog.afterClosed().subscribe(result => {
              console.log('dialog after closed OnInit-Methode');
              this.ngOnInit();
            });
          });
      });
  }


  openPriorityModal(deviceId: string) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.id = 'priority-component';
    dialogConfig.height = '200px';
    dialogConfig.width = '400px';

    // 1. erst Daten des Dialogs laden
    this.stateService.getState(deviceId)
      .subscribe((state: State) => {

        dialogConfig.data = {deviceId, state};

        // 2. mit geladenen Daten den Dialog öffnen:
        // https://material.angular.io/components/dialog/overview
        const modalDialog = this.matDialog.open(PriorityChangeDialogComponent, dialogConfig);
      });
  }

  openAddInfoModal(deviceId: string) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.id = 'add-additional-info-component';
    dialogConfig.height = '250px';
    dialogConfig.width = '400px';

    dialogConfig.data = {deviceId};

    const modalDialog = this.matDialog.open(AddAdditionalInformationDialogComponent, dialogConfig);
  }

  openAddInitalStateModal(deviceId: string) {
    const dialogConfig = DialogHelper.setDefaultInfoDialogConfig('add-intial-state-info-component',
      'Add Initial State', 'Do you want to activate state control?', true, true);

    dialogConfig.data = Object.assign(dialogConfig.data, {
      deviceId
    });

    const modalDialog = this.matDialog.open(InfoDialogComponent, dialogConfig);
  }

  openRemoveStateModal(deviceId: string) {
    const dialogConfig = DialogHelper.setDefaultInfoDialogConfig('remove-state-info-component',
      'Remove State', 'Do you want to deactivate state control?', true, true);

    dialogConfig.data = Object.assign(dialogConfig.data, {
      deviceId
    });

    const modalDialog = this.matDialog.open(InfoDialogComponent, dialogConfig);
  }

  openChangeAvailabilityDateModal(deviceId: string, availabilityDate: string) {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.id = 'change-availability-date-component';
    dialogConfig.height = '250px';
    dialogConfig.width = '400px';

    console.log(availabilityDate);

    dialogConfig.data = {
      deviceId,
      availabilityDate
    };

    const modalDialog = this.matDialog.open(ChangeAvailabilityDateDialogComponent, dialogConfig);
  }

  public applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


}

