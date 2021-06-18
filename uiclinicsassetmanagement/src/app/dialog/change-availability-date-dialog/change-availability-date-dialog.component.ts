import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {HelperService} from '../../services/helper.service';
import {StateService} from '../../services/state.service';

@Component({
  selector: 'app-change-availability-date-dialog',
  templateUrl: './change-availability-date-dialog.component.html',
  styleUrls: ['./change-availability-date-dialog.component.scss']
})
export class ChangeAvailabilityDateDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ChangeAvailabilityDateDialogComponent>,
    private stateService: StateService,
    private helperService: HelperService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    if (this.data.availabilityDate){
      this.data.currentDate = this.helperService.applyDateFormat(this.data.availabilityDate, this.helperService.DATE_FORMAT);
      data.newDate = this.data.availabilityDate;  // aktuellen Wert in Picker setzen
    }
  }

  ngOnInit(): void { }

  actionFunction() {

    if (this.data.newDate)
    {
      let obj = {
        availabilityDate: this.helperService.applyDateFormat(this.data.newDate, this.helperService.DATE_FORMAT_API)
      };

      this.stateService.postData('setAvailabilityDate', this.data.deviceId, null, true, obj)
        .subscribe(() => {
          console.log('setAvailabilityDate successful!');

          this.closeModal();
          window.location.reload();
        });
    }
  }

  closeModal() {
    this.dialogRef.close();
  }
}
