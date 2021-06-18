// This is also a typescript file which includes all the dependencies for the website.
// This file is used to define the needed modules to be imported, the components to be
// declared and the main component to be bootstrapped.

import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {DataTablesModule} from 'angular-datatables';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatTableModule} from '@angular/material/table';
import {MatButtonModule} from '@angular/material/button';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {LayoutModule} from '@angular/cdk/layout';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list';
import {DeviceStateTableComponent} from './device-state-table/device-state-table.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatDialogModule} from '@angular/material/dialog';

import {ChangeStateDialogComponent} from './dialog/change-state-dialog/change-state-dialog.component';
import {InfoDialogComponent} from './dialog/info-dialog/info-dialog.component';
import {ListDialogComponent} from './dialog/list-dialog/list-dialog.component';
import {PriorityChangeDialogComponent} from './dialog/priority-change-dialog/priority-change-dialog.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MAT_DATE_LOCALE, MatNativeDateModule, MatOptionModule} from '@angular/material/core';
import {MatSelectModule} from '@angular/material/select';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {ChangeUserDialogComponent} from './dialog/change-user-dialog/change-user-dialog.component';
import {CookieService} from 'ngx-cookie-service';
import { AddAdditionalInformationDialogComponent } from './dialog/add-additional-information-dialog/add-additional-information-dialog.component';
import { CdkTableModule } from '@angular/cdk/table';
import { ChangeAvailabilityDateDialogComponent } from './dialog/change-availability-date-dialog/change-availability-date-dialog.component';
import { UserLevelSettingDialogComponent } from './dialog/user-level-setting-dialog/user-level-setting-dialog.component';
import {MatStepper, MatStepperModule} from '@angular/material/stepper';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatCheckboxModule} from '@angular/material/checkbox';


@NgModule({
  declarations: [
    AppComponent,
    DeviceStateTableComponent,
    ChangeStateDialogComponent,
    InfoDialogComponent,
    ListDialogComponent,
    PriorityChangeDialogComponent,
    ChangeUserDialogComponent,
    AddAdditionalInformationDialogComponent,
    ChangeAvailabilityDateDialogComponent,
    UserLevelSettingDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    DataTablesModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatButtonModule,
    MatPaginatorModule,
    MatSortModule,
    LayoutModule,
    MatToolbarModule,
    MatSidenavModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatListModule,
    MatDialogModule,
    MatInputModule,
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    ReactiveFormsModule,
    MatStepperModule,
    MatStepperModule,
    DragDropModule,
    DragDropModule
  ],
  providers: [
    CookieService,
    {provide: MAT_DATE_LOCALE, useValue: 'de-DE'},
  ],
  bootstrap: [AppComponent],
  entryComponents: [ChangeStateDialogComponent],
  exports: [
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatSortModule,
    CdkTableModule,
    MatDialogModule,
    MatTableModule,
    MatStepper
  ]
})
export class AppModule {
}

