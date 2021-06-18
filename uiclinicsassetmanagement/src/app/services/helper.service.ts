import {Injectable} from '@angular/core';
import * as moment from 'moment';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {InfoDialogComponent} from '../dialog/info-dialog/info-dialog.component';
import {ListDialogComponent} from '../dialog/list-dialog/list-dialog.component';
import {CookieService} from 'ngx-cookie-service';
import {State} from '../model/state';
import {StateService} from './state.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HelperService {

  constructor(
    private matDialog: MatDialog,
    private cookieService: CookieService,
    private stateService: StateService
  ) {
  }

  DATE_FORMAT = 'DD.MM.YYYY';
  DATE_FORMAT_API = 'YYYY-MM-DD';
  TIME_FORMAT = 'HH:mm';
  TIME_LONG_FORMAT = 'HH:mm:ss';
  FULL_FORMAT = this.DATE_FORMAT + ' ' + this.TIME_FORMAT;

  applyDateFormat(dateTime: string, format = this.FULL_FORMAT): string | null {
    if (!dateTime || dateTime === 'null') {
      return null;
    }

    return moment(dateTime).format(format).toString();
  }

  additionalInfoHelper(data: any): string {
    let output = '';
    if (data){
      data.forEach(x => {
        if (x.information) {
          output += x.information + '\n\r';
        }
      });
    }
    console.log(output);
    return output;
  }

  // SHOW_CHANGE_STATE_BUTTON = (id: string): any => {
  //   this.stateService.getData('getPossibleNextStates', id, null, true)
  //     .subscribe((posNextStates: State[]) => {
  //       if (posNextStates) {
  //         if (posNextStates.length == 0) {
  //           return false;
  //         }
  //       }
  //       return true;
  //     });
  // }
  //
  // // "helperService.checkRight(helperService.showChangeStateButton, device.deviceId)"
  // checkRight(checkFkt: (id: string, user: string) => boolean, deviceId: string): boolean {
  //   console.log('checkRight for device: ' + deviceId);
  //   return checkFkt(deviceId, this.cookieService.get('userId'));
  // }
}
