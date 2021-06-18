import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {StateService} from './state.service';
import {Inject, Injectable} from '@angular/core';
import {InfoDialogComponent} from '../dialog/info-dialog/info-dialog.component';
import {ListDialogComponent} from '../dialog/list-dialog/list-dialog.component';

@Injectable()
export abstract class DialogHelper {

  // constructor(public matDialog: MatDialog) {
  // }

  static setDefaultErrorDialogConfig(text: string): MatDialogConfig {

    return this.setDefaultInfoDialogConfig('errorDialog', 'Leider ist ein Fehler aufgetreten', text, true, false);
  }


  static setDefaultInfoDialogConfig(id: string, title: string, text: string, showOkButton = true, showCancelButton = true): MatDialogConfig {
    const config = new MatDialogConfig();

    config.disableClose = false;
    config.id = id;

    config.height = '200px';
    config.width = '400px';

    config.data = {
      name: id, // Verknüpfung mit Action
      title: title,
      description: text,

      showOkButton: showOkButton,
      actionButtonText: 'Ok',

      showCancelButton: showCancelButton,
      cancelButtonText: 'Cancel'
    };

    return config;
  }

  static setDefaultListDialogConfig(id: string, title: string, url: string): MatDialogConfig {
    const config = new MatDialogConfig();

    config.disableClose = false;
    config.id = id;

    config.height = '500px';
    config.width = '800px';

    config.data = {
      name: id, // Verknüpfung mit Action
      title: title,

      showCloseButton: true,
      closeButtonText: 'Close',

      url: url
    };

    return config;
  }


  // public static openErrorDialog(matDialog: MatDialog, errorMessage: string): MatDialogRef<InfoDialogComponent, any> {
  //   const dialogConfig = this.setDefaultInfoDialogConfig('errorDialog', 'Leider ist ein Fehler aufgetreten', errorMessage, true, false);
  //
  //   const modalDialog = matDialog.open(InfoDialogComponent, dialogConfig);
  //
  //   return modalDialog;
  // }
  //
  // public static openListDialog(matDialog: MatDialog, id: string, title: string, apiCall: string, apiCallId: string): MatDialogRef<ListDialogComponent, any> {
  //   const dialogConfig = this.setDefaultListDialogConfig(id, title, apiCall, apiCallId);
  //
  //   const modalDialog = matDialog.open(ListDialogComponent, dialogConfig);
  //
  //   return modalDialog;
  // }

}
