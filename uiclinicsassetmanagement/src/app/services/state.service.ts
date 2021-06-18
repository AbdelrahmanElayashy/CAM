import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {ChangedPriority, ChangedState, State} from '../model/state';
import {catchError} from 'rxjs/operators';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {InfoDialogComponent} from '../dialog/info-dialog/info-dialog.component';
import {CookieService} from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class StateService {

  private baseUrlREST = 'http://localhost:8080/stateHandler/';

  constructor(private http: HttpClient,
              public matDialog: MatDialog,
              private cookieService: CookieService
  ) {
  }

  public getCall(url: string): Observable<any> {
    return this.http.get(url);
  }

  public postCall(url: string, obj: any): Observable<any> {
    return this.http.post(url, obj);
  }

  public getData(apiCall: string, id?: string, param?: Map<string, string>, addUserId = false): Observable<any> {
    return this.getCall(this.restUrl(apiCall, id, param, addUserId));
  }

  public postData(apiCall: string, id?: string, param?: Map<string, string>, addUserId = false, obj: any = null): Observable<any> {
    return this.postCall(this.restUrl(apiCall, id, param, addUserId), obj);
  }

  getDeviceList(): Observable<any> {
    return this.http.get(`${this.baseUrlREST}` + 'getDeviceList');
  }

  getDevice(id: number): Observable<object> {
    return this.http.get(`${this.baseUrlREST}/device/${id}`);
  }

  getState(deviceId: string): any {
    return this.http.get(this.restUrl('getState', deviceId, null, true));
  }

  changeState = (deviceId: string, newState: string) => {
    let obj = {
      state: newState
    };

    return this.http.post(this.restUrl('changeState', deviceId, null, true), obj);
  }

  changePriority = (deviceId: string, newPriority: string) => {
    let obj = {
      priority: newPriority
    };

    return this.http.post(this.restUrl('setDefectPriority', deviceId, null, true), obj);
  }


  /// region Helper

  restUrl(apiCall: string, id?: string, parameters?: Map<string, string>, addUserId = false): string {
    return this.createUrl(this.baseUrlREST, apiCall, id, parameters, addUserId);
  }

  createUrl(api: string, apiCall: string, id?: string, parameters?: Map<string, string>, addUserId = false): string {
    let url = api + apiCall;

    if (id) {
      url += '/' + id;
    }

    url += '?';

    if (addUserId) {
      url += 'userId=' + this.cookieService.get('userId') + '&';
    }
    if (parameters) {
      parameters.forEach((value, key) => {
        url += key + '=' + value + '&';
      });
    }

    console.log('url: ' + url);
    return url;
  }
}
