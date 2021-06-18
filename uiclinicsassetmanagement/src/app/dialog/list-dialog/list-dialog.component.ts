import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {MatTableDataSource} from '@angular/material/table';
import {StateService} from '../../services/state.service';
import {State} from '../../model/state';
import {HelperService} from '../../services/helper.service';
import {DialogHelper} from '../../services/dialogHelper';
import {InfoDialogComponent} from '../info-dialog/info-dialog.component';

@Component({
  selector: 'app-list-dialog',
  templateUrl: './list-dialog.component.html',
  styleUrls: ['./list-dialog.component.scss']
})
export class ListDialogComponent implements OnInit {

  public dataSource = new MatTableDataSource<State>();
  displayedColumns: string[] = ['col1', 'col2', 'col3', 'col4', 'col5'];

  constructor(
    public dialogRef: MatDialogRef<ListDialogComponent>,
    private stateService: StateService,
    public helperService: HelperService,
    public matDialog: MatDialog,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {
  }

  ngOnInit(): void {

    const borders = {from: null, to: null};

    this.stateService.postCall(this.data.url, borders)
      .subscribe(response => {

        this.dataSource.data = response.states as State[];

      }, error => {
        const dialogConfig = DialogHelper.setDefaultErrorDialogConfig(error.error.message);
        const modalDialog = this.matDialog.open(InfoDialogComponent, dialogConfig);

        throw error;  // Error erstmal trotzdem werfen
      });
  }

  closeFunction() {
    this.dialogRef.close();
  }
}
