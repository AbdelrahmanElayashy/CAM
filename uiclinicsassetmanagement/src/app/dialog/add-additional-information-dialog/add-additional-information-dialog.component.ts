import { Component, Inject, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {DialogHelper} from '../../services/dialogHelper';
import {InfoDialogComponent} from '../info-dialog/info-dialog.component';
import {HelperService} from '../../services/helper.service';
import {StateService} from '../../services/state.service';

@Component({
  selector: 'app-add-additional-information-dialog',
  templateUrl: './add-additional-information-dialog.component.html',
  styleUrls: ['./add-additional-information-dialog.component.scss']
})
export class AddAdditionalInformationDialogComponent implements OnInit {

  private deviceId: string;

  constructor(
    private stateService: StateService,
    private dialogRef: MatDialogRef<AddAdditionalInformationDialogComponent>,
    private helperService: HelperService,
    private matDialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    if (this.data) {
      this.deviceId = this.data.deviceId;
    }
  }

  addInfo: string;

  ngOnInit(): void {
  }

  // When the user clicks the action button
  actionFunction() {


    console.log('Add Additional Information: ' + this.addInfo);

    // addAdditionalInformation

    let obj = {
      information: this.addInfo
    };

    this.stateService.postData('addAdditionalInformation', this.deviceId, null, true, obj)
       .subscribe((data: {}) => {

         console.log('addAdditionalInformation erfolgreich');

         this.closeModal();
       }, error => {

          const dialogConfig = DialogHelper.setDefaultErrorDialogConfig( error.error.message);
          const modalDialog = this.matDialog.open(InfoDialogComponent, dialogConfig);
          throw error;  // Error erstmal trotzdem werfen
        });
  }


  closeModal() {
    // TODO evtl. Error-Dialog zuerst schließen
    this.dialogRef.close();
    // this.closeModal();
  }
}
