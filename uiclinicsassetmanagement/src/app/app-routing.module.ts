import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DeviceStateTableComponent} from './device-state-table/device-state-table.component';

const routes: Routes = [
  { path: '', redirectTo: 'view-device', pathMatch: 'full' },
  { path: 'view-device-states', component: DeviceStateTableComponent },
  { path: 'view-device-states-expandable', component: DeviceStateTableComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
