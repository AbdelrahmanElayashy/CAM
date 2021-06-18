import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material/dialog';
import {CookieService} from 'ngx-cookie-service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StaffService {

  private baseUrlREST = 'http://localhost:8082/staffManager/';

  constructor(private http: HttpClient,
              public matDialog: MatDialog,
              private cookieService: CookieService
  ) {
  }

  public getData(apiCall: string, id?: string, param?: Map<string, string>, addUserId = false): Observable<any> {
    return this.getCall(this.restUrl(apiCall, id, param, addUserId));
  }

  public postData(apiCall: string, id?: string, param?: Map<string, string>, addUserId = false, obj: any = null): Observable<any> {
    return this.postCall(this.restUrl(apiCall, id, param, addUserId), obj);
  }

  public getCall(url: string): Observable<any> {
    return this.http.get(url);
  }

  public postCall(url: string, obj: any): Observable<any> {
    return this.http.post(url, obj);
  }

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
