import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {DeviceStateViewRow} from '../model/deviceStateViewRow';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {StateService} from './state.service';
import {catchError, filter, finalize} from 'rxjs/operators';

export class DevicesDatasource implements DataSource<DeviceStateViewRow> {

  private devicesSubject = new BehaviorSubject<DeviceStateViewRow[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  public loading$ = this.loadingSubject.asObservable();

  constructor(private stateService: StateService) {  }

  loadDevices() {

    this.loadingSubject.next(true);

    this.stateService.getDeviceList().pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    )
      .subscribe(devices => this.devicesSubject.next(devices));
  }

  connect(collectionViewer: CollectionViewer): Observable<DeviceStateViewRow[]> {
    console.log('Connecting data source');
    return this.devicesSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.devicesSubject.complete();
    this.loadingSubject.complete();
  }

}
