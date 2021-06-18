import {Component, OnInit, Inject, Injectable} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {StateService} from '../../services/state.service';
import {ChangedState, State} from '../../model/state';
import {DialogHelper} from '../../services/dialogHelper';
import {HelperService} from '../../services/helper.service';
import {InfoDialogComponent} from '../info-dialog/info-dialog.component';


@Component({
  selector: 'app-modal',
  templateUrl: './change-state-dialog.component.html',
  styleUrls: ['./change-state-dialog.component.scss']
})

export class ChangeStateDialogComponent implements OnInit {

  deviceId!: string;
  state!: State | any;
  nextStates: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<ChangeStateDialogComponent>,
    public matDialog: MatDialog,
    private stateService: StateService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {

    if (this.data) {
      console.log(this.data);
      console.log(this.state);
      this.deviceId = this.data.deviceId;
      this.state = this.data.curState;

      if (this.data.posNextStates) {

        if (this.data.posNextStates.length == 0){
          // User hat keine Rechte auf eine Statusänderung
          console.log('ccc');

        }

        console.log(this.data.posNextStates);

        this.data.posNextStates.forEach(x => {
          this.nextStates.push({id: x, name: x});
        });
        // this.nextStates = this.data.posNextStates.map(x => { 'id': x.name, name: x.name});
        // this.nextStates = this.data.posNextStates;
      }

      // this.selected = this.state.state;
    }
  }

  items: any[] = [
    {id: 'AVAILABLE', name: 'available'},
    {id: 'RESERVED', name: 'reserved'},
    {id: 'IN_USE', name: 'in use'},
    {id: 'CLEANING', name: 'cleaning'},
    {id: 'DEFECT_INSPECTION', name: 'defect inspection'},
    {id: 'MAINTENANCE', name: 'maintenance'},
    {id: 'DEFECT_NO_REPAIR', name: 'defect no repair'},
    {id: 'DISPOSED', name: 'disposed'},
    {id: 'DEFECT_EXTERNAL_REPAIR', name: 'defect external repair'},
    {id: 'DEFECT_OWN_REPAIR', name: 'defect own repair'}
  ];
  selected = 'AVAILABLE'; // default-Wert, wird vom Konstruktor überschrieben

  ngOnInit() {

    // UI-Elemente geladen, nun Dropdown-Liste füllen:
    // const nextStateNames = new Map();
    // nextStateNames.set('AVAILABLE', 'verfügbar');
    // nextStateNames.set('RESERVED', 'reserviert');
    // nextStateNames.set('IN_USE', 'in Verwendung');
    // nextStateNames.set('CLEANING', 'in Reinigung');
    //
    // // let options: HTMLOptionElement[];
    // const options: HTMLOptionElement[] = new Array();
    //
    // nextStateNames.forEach((val, key) => {
    //   const htmlObject = document.createElement('option');
    //   htmlObject.innerHTML = '<option value=\"' + key + '\"> ' + val + ' </option>';
    //   options.push(htmlObject);
    // });
    //
    // console.log(options);
    //
    // const element = $('select[name ="nextStatesSelectbox"]')[0];
    // options.forEach(option => element.appendChild(option));
  }


  selectOption(event: Event) {
    const id: string = (event.target as HTMLTextAreaElement).value;
    console.log(id);
    // console.log(this.selected); // alternativ möglich
  }

  // When the user clicks the action button
  actionFunction() {
    console.log('Next Status: ' + this.selected);

    // jetzt ChangeState aufrufen
    const userId = this.deviceId; // TODO: userID !!!

    this.stateService.changeState(this.deviceId, this.selected)
      .subscribe((data: {}) => {

        console.log('changeState erfolgreich');

        // ChangeState-Dialog erstmal schließen, TODO "Statuswechsel erfolgreich"-Meldung?
        this.closeModal();
        // window.location.reload();

      }, error => {

        const dialogConfig = DialogHelper.setDefaultErrorDialogConfig(error.error.message);
        const modalDialog = this.matDialog.open(InfoDialogComponent, dialogConfig);
        throw error;  // Error erstmal trotzdem werfen
      });

    // this.closeModal();
  }

  // If the user clicks the cancel button
  closeModal() {
    // TODO evtl. Error-Dialog zuerst schließen
    this.dialogRef.close();
    // this.closeModal();
  }
}
