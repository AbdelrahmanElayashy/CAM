import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {StateService} from './state.service';

@Injectable({
  providedIn: 'root'
})
export class MedicalDeviceService {

  // Service der auf den (aktuell gemocked, aber deployed(!)) Medical Device Microservice zugreift

  constructor(private http: HttpClient, private stateService: StateService) { }

  // private medicalDeviceBaseUrl = 'http://cm-medicaldevicemock.cloud.iai.kit.edu/medicalDevice/';
  private medicalDeviceBaseUrl = 'http://localhost:8081/medicalDevice/';

  public getData(apiCall: string, id?: string): Observable<any> {
    return this.http.get(this.stateService.createUrl(this.medicalDeviceBaseUrl, apiCall, id));  // TODO: eigene URl-Methoden
  }

}
