import { Injectable } from '@angular/core';

import {grpc} from '@improbable-eng/grpc-web';
import {StandardRequest, StatesByIdRequest, StatesByIdResponse, StatesResponse} from '../proto/stateTest_pb';
import {StateService, StateServiceClient, StateServicegetStatesByIds} from '../proto/stateTest_pb_service';
import * as google_protobuf_empty_pb from 'google-protobuf/google/protobuf/empty_pb';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class StateGRPCService {
  private myAppUrl: string;
  client: StateServiceClient;

  constructor(private http: HttpClient) {
    this.myAppUrl = environment.grpcUrl;
    this.client = new StateServiceClient(this.myAppUrl);
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

  public getPossibleNextStates(req: StandardRequest): Promise<StatesResponse> {
    return new Promise((resolve, reject) => {
      this.client.possibleNextStates(req, (err, response: StatesResponse) => {
        if (err) {
          return reject(err);
        }
        resolve(response);
      });
    });
  }
}
