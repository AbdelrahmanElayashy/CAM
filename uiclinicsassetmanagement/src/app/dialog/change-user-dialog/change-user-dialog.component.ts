import {Component, OnInit} from '@angular/core';
import {DialogHelper} from '../../services/dialogHelper';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {CookieService} from 'ngx-cookie-service';
import {InfoDialogComponent} from '../info-dialog/info-dialog.component';
import {StaffService} from '../../services/staff.service';

@Component({
  selector: 'app-change-user',
  templateUrl: './change-user-dialog.component.html',
  styleUrls: ['./change-user-dialog.component.scss']
})
export class ChangeUserDialogComponent implements OnInit {

  username: string;
  password: string;

  constructor(
    public dialogRef: MatDialogRef<ChangeUserDialogComponent>,
    private staffService: StaffService,
    private cookieService: CookieService,
    private matDialog: MatDialog
  ) {
    // let userId = this.cookieService.get('userId');
    // if (userId) {
    //   // userId initial setzen
    //   this.currentUserLevel = this.items.filter(x => x.id === userId)[0].name; // bisheriges UserLevel
    //   this.selected = userId;
    // }

    console.log('superuser password');
    console.log('medicalNormal 0000');
    console.log('technicalNormal 1234');
    console.log('technicalAdmin 5678');
  }

  ngOnInit(): void {
  }

  actionFunction() {

    const param = new Map<string, string>();
    param.set('username', this.username);
    param.set('password', this.password);

    this.staffService.postData('login', null, param, false, null)
      .subscribe((userLevelId) => {

        console.log('login erfolgreich - id ins cookie setzen');
        this.cookieService.set( 'userId', userLevelId);

        this.closeModal();

        console.log('window reload');
        window.location.reload();

      }, error => {

        const dialogConfig = DialogHelper.setDefaultErrorDialogConfig(error.error.message);
        this.matDialog.open(InfoDialogComponent, dialogConfig);
      });
  }

  // If the user clicks the cancel button
  closeModal() {
    // TODO evtl. Error-Dialog zuerst schließen
    this.dialogRef.close();
    // this.closeModal();
  }
}
