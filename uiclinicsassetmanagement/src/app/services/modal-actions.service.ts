import { Injectable } from '@angular/core';
import {State} from '../model/state';
import {PriorityChangeDialogComponent} from '../dialog/priority-change-dialog/priority-change-dialog.component';
import {StateService} from './state.service';

@Injectable({
  providedIn: 'root'
})
export class ModalActionsService {

  constructor(
    private stateService: StateService
  ) { }

  modalAction(modalData: any) {
    switch (modalData.name) {
      case 'errorDialog':
        // bei error-Dialogen nichts weiter zu tun.
        break;

      case 'add-intial-state-info-component':
        this.addInitialState(modalData);
        break;

      case 'remove-state-info-component':
        this.removeState(modalData);
        break;


      default:
        console.log('not implemented Dialog-Action for modalData.name: ' + modalData.name);
        break;
    }
  }

  private addInitialState(modalData: any) {

    let obj = {
      deviceId: modalData.deviceId,
      state: "AVAILABLE"
    };

    this.stateService.postData( 'addInitialState', null, null, true, obj)
      .subscribe(x => {

        console.log('addInitialState successful');
        window.location.reload();
      }, error => {
        throw error;
      });
  }

  private removeState(modalData: any){
    this.stateService.postData('removeState', modalData.deviceId, null, true)
      .subscribe(x => {

        console.log('removeState successful');
        window.location.reload();
      }, error => {
        throw error;
      });
  }
}
