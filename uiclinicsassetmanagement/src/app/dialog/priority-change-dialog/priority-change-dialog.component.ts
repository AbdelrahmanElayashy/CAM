import {Component, Inject, OnInit} from '@angular/core';
import {State} from '../../model/state';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {StateService} from '../../services/state.service';
import {DialogHelper} from '../../services/dialogHelper';
import {HelperService} from '../../services/helper.service';
import {InfoDialogComponent} from '../info-dialog/info-dialog.component';

@Component({
  selector: 'app-priority-change-dialog',
  templateUrl: './priority-change-dialog.component.html',
  styleUrls: ['./priority-change-dialog.component.scss']
})
export class PriorityChangeDialogComponent implements OnInit {

  deviceId!: string;
  state!: State | any;

  constructor(
    public dialogRef: MatDialogRef<PriorityChangeDialogComponent>,
    public matDialog: MatDialog,
    private stateService: StateService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {

    if (this.data) {
      this.deviceId = this.data.deviceId;
      this.state = this.data.state;

      this.selected = this.data.state.defectPriority;
    }
  }

  items: any[] = [
    {id: 'HIGH', name: 'HIGH'},
    {id: 'MEDIUM', name: 'MEDIUM'},
    {id: 'LOW', name: 'LOW'}
  ];
  selected = 'HIGH'; // default-Wert, wird vom Konstruktor überschrieben

  ngOnInit(): void {
  }

  selectOption(event: Event) {
    const id: string = (event.target as HTMLTextAreaElement).value;
    // getted from binding
    console.log(this.selected);
  }

  // When the user clicks the action button
  actionFunction() {
    console.log('Next Priority: ' + this.selected);
    console.log('DeviceID: ' + this.deviceId);

    // jetzt ChangeState aufrufen
    const userId = this.deviceId; // TODO: userID !!!

    this.stateService.changePriority(this.deviceId, this.selected)
      .subscribe((data: {}) => {

        console.log('changePriority erfolgreich');

        // ChangeState-Dialog erstmal schließen, TODO "Statuswechsel erfolgreich"-Meldung?
        this.closeModal();

        console.log('window reload');
        window.location.reload();

      }, error => {

        const dialogConfig = DialogHelper.setDefaultErrorDialogConfig(error.error.message);
        const modalDialog = this.matDialog.open(InfoDialogComponent, dialogConfig);
      });

    // this.closeModal();
  }

  closeModal() {
    // TODO evtl. Error-Dialog zuerst schließen
    this.dialogRef.close();
    // this.closeModal();
  }
}
