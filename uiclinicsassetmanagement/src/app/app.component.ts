import { Component } from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ChangeUserDialogComponent} from './dialog/change-user-dialog/change-user-dialog.component';
import {UserLevelSettingDialogComponent} from './dialog/user-level-setting-dialog/user-level-setting-dialog.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = '';

  constructor( public matDialog: MatDialog ) {
  }


  setTitle(newTitle: string) {
    this.title = newTitle;
  }

  openUserDialog(){
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.id = 'change-user-component';
    dialogConfig.height = '280px';
    dialogConfig.width = '400px';

    this.matDialog.open(ChangeUserDialogComponent, dialogConfig);
  }

  openUserLevelSettingDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.id = 'user-level-setting-component';
    dialogConfig.height = '400px';
    dialogConfig.width = '500px';

    this.matDialog.open(UserLevelSettingDialogComponent, dialogConfig);
  }
}


