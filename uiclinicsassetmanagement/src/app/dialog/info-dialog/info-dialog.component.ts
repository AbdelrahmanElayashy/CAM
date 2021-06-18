import {Component, Inject, OnInit} from '@angular/core';
import {State} from '../../model/state';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {StateService} from '../../services/state.service';
import {ModalActionsService} from '../../services/modal-actions.service';

@Component({
  selector: 'app-error-dialog',
  templateUrl: './info-dialog.component.html',
  styleUrls: ['./info-dialog.component.scss']
})
export class InfoDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<InfoDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private modalService: ModalActionsService
  ) {

    // this.data.title = 'title';
    // this.data.description = 'description';
    // this.data.showOkButton = true;
    // this.data.actionButtonText = 'Ok';
    //
    // this.data.showCancelButton = true;
    // this.data.cancelButtonText = 'Cancel';
  }

  ngOnInit(): void {
  }

  okFunction() {
    this.modalService.modalAction(this.data);
    this.dialogRef.close(); // evtl. das noch rausziehen..
  }

  cancelFunction() {
    this.dialogRef.close();
  }
}
