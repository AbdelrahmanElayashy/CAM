import {StateServiceClient} from 'src/app/proto/stateTest_pb_service';
import {
  StandardRequest, StateResponse, InitialStateRequest, HistoryResponse,
  HistoryRequest, ChangeRequest, SetPriorityRequest, SetAvailabilityRequest,
  AdditionalInformationRequest, StatesResponse, StatesByIdRequest, StatesByIdResponse
} from 'src/app/proto/stateTest_pb';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import {Empty} from 'google-protobuf/google/protobuf/empty_pb';

@Injectable({
  providedIn: 'root'
})
export class StateManagerGRPCService {
  private myAppUrl: string;
  client: StateServiceClient;

  constructor(private http: HttpClient) {
    this.myAppUrl = environment.grpcUrl;
    this.client = new StateServiceClient(this.myAppUrl);
  }

  public initialState(req: InitialStateRequest): Promise <void> {
    return new Promise((resolve, reject) => {
      this.client.initialState(req, null, (err, response: Empty) => {
        if (err) {
          return reject(err);
        }
        resolve();
      });
    });
  }

  public removeState(req: StandardRequest): Promise <void> {
    return new Promise((resolve, reject) => {
      this.client.removeState(req, null, (err, response: Empty) => {
        if (err) {
          return reject(err);
        }
        resolve();
      });
    });
  }

  public deleteHistory(req: StandardRequest): Promise <void> {
    return new Promise((resolve, reject) => {
      this.client.deleteHistory(req, null, (err, response: Empty) => {
        if (err) {
          return reject(err);
        }
        resolve();
      });
    });
  }

  public state(req: StandardRequest): Promise <StateResponse> {
    return new Promise((resolve, reject) => {
      this.client.state(req, null, (err, response: StateResponse) => {
        if (err) {
          return reject(err);
        }
        resolve(response);
      });
    });
  }

  public history(req: HistoryRequest): Promise <HistoryResponse> {
    return new Promise((resolve, reject) => {
      this.client.history(req, null, (err, response: HistoryResponse) => {
        if (err) {
          return reject(err);
        }
        resolve(response);
      });
    });
  }


  public change(req: ChangeRequest): Promise <void> {
    return new Promise((resolve, reject) => {
      this.client.change(req, null, (err, response: Empty) => {
        if (err) {
          return reject(err);
        }
        resolve();
      });
    });
  }

  public setPriority(req: SetPriorityRequest): Promise <void> {
    return new Promise((resolve, reject) => {
      this.client.setPriority(req, null, (err, response: Empty) => {
        if (err) {
          return reject(err);
        }
        resolve();
      });
    });
  }

  public setAvailability(req: SetAvailabilityRequest): Promise <void> {
    return new Promise((resolve, reject) => {
      this.client.setAvailability(req, null, (err, response: Empty) => {
        if (err) {
          return reject(err);
        }
        resolve();
      });
    });
  }

  public additionalInformation(req: AdditionalInformationRequest): Promise <void> {
    return new Promise((resolve, reject) => {
      this.client.additionalInformation(req, null, (err, response: Empty) => {
        if (err) {
          return reject(err);
        }
        resolve();
      });
    });
  }
  public possibleNextStates(req: StandardRequest): Promise <StatesResponse> {
    return new Promise((resolve, reject) => {
      this.client.possibleNextStates(req, null, (err, response: StatesResponse) => {
        if (err) {
          return reject(err);
        }
        resolve(response);
      });
    });
  }

  public getStatesByIds(req: StatesByIdRequest): Promise <StatesByIdResponse> {
    return new Promise((resolve, reject) => {
      this.client.getStatesByIds(req, null, (err, response: StatesByIdResponse) => {
        if (err) {
          return reject(err);
        }
        resolve(response);
      });
    });
  }


}




