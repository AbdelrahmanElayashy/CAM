// package: com.example.demo.grpc
// file: stateTest.proto

import * as stateTest_pb from "./stateTest_pb";
import * as google_protobuf_empty_pb from "google-protobuf/google/protobuf/empty_pb";
import {grpc} from "@improbable-eng/grpc-web";

type StateServiceinitialState = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.InitialStateRequest;
  readonly responseType: typeof google_protobuf_empty_pb.Empty;
};

type StateServiceremoveState = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.StandardRequest;
  readonly responseType: typeof google_protobuf_empty_pb.Empty;
};

type StateServicedeleteHistory = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.StandardRequest;
  readonly responseType: typeof google_protobuf_empty_pb.Empty;
};

type StateServicestate = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.StandardRequest;
  readonly responseType: typeof stateTest_pb.StateResponse;
};

type StateServicehistory = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.HistoryRequest;
  readonly responseType: typeof stateTest_pb.HistoryResponse;
};

type StateServicechange = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.ChangeRequest;
  readonly responseType: typeof google_protobuf_empty_pb.Empty;
};

type StateServicesetPriority = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.SetPriorityRequest;
  readonly responseType: typeof google_protobuf_empty_pb.Empty;
};

type StateServicesetAvailability = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.SetAvailabilityRequest;
  readonly responseType: typeof google_protobuf_empty_pb.Empty;
};

type StateServiceadditionalInformation = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.AdditionalInformationRequest;
  readonly responseType: typeof google_protobuf_empty_pb.Empty;
};

type StateServicepossibleNextStates = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.StandardRequest;
  readonly responseType: typeof stateTest_pb.StatesResponse;
};

type StateServicegetStatesByIds = {
  readonly methodName: string;
  readonly service: typeof StateService;
  readonly requestStream: false;
  readonly responseStream: false;
  readonly requestType: typeof stateTest_pb.StatesByIdRequest;
  readonly responseType: typeof stateTest_pb.StatesByIdResponse;
};

export class StateService {
  static readonly serviceName: string;
  static readonly initialState: StateServiceinitialState;
  static readonly removeState: StateServiceremoveState;
  static readonly deleteHistory: StateServicedeleteHistory;
  static readonly state: StateServicestate;
  static readonly history: StateServicehistory;
  static readonly change: StateServicechange;
  static readonly setPriority: StateServicesetPriority;
  static readonly setAvailability: StateServicesetAvailability;
  static readonly additionalInformation: StateServiceadditionalInformation;
  static readonly possibleNextStates: StateServicepossibleNextStates;
  static readonly getStatesByIds: StateServicegetStatesByIds;
}

export type ServiceError = { message: string, code: number; metadata: grpc.Metadata }
export type Status = { details: string, code: number; metadata: grpc.Metadata }

interface UnaryResponse {
  cancel(): void;
}
interface ResponseStream<T> {
  cancel(): void;
  on(type: 'data', handler: (message: T) => void): ResponseStream<T>;
  on(type: 'end', handler: (status?: Status) => void): ResponseStream<T>;
  on(type: 'status', handler: (status: Status) => void): ResponseStream<T>;
}
interface RequestStream<T> {
  write(message: T): RequestStream<T>;
  end(): void;
  cancel(): void;
  on(type: 'end', handler: (status?: Status) => void): RequestStream<T>;
  on(type: 'status', handler: (status: Status) => void): RequestStream<T>;
}
interface BidirectionalStream<ReqT, ResT> {
  write(message: ReqT): BidirectionalStream<ReqT, ResT>;
  end(): void;
  cancel(): void;
  on(type: 'data', handler: (message: ResT) => void): BidirectionalStream<ReqT, ResT>;
  on(type: 'end', handler: (status?: Status) => void): BidirectionalStream<ReqT, ResT>;
  on(type: 'status', handler: (status: Status) => void): BidirectionalStream<ReqT, ResT>;
}

export class StateServiceClient {
  readonly serviceHost: string;

  constructor(serviceHost: string, options?: grpc.RpcOptions);
  initialState(
    requestMessage: stateTest_pb.InitialStateRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  initialState(
    requestMessage: stateTest_pb.InitialStateRequest,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  removeState(
    requestMessage: stateTest_pb.StandardRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  removeState(
    requestMessage: stateTest_pb.StandardRequest,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  deleteHistory(
    requestMessage: stateTest_pb.StandardRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  deleteHistory(
    requestMessage: stateTest_pb.StandardRequest,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  state(
    requestMessage: stateTest_pb.StandardRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: stateTest_pb.StateResponse|null) => void
  ): UnaryResponse;
  state(
    requestMessage: stateTest_pb.StandardRequest,
    callback: (error: ServiceError|null, responseMessage: stateTest_pb.StateResponse|null) => void
  ): UnaryResponse;
  history(
    requestMessage: stateTest_pb.HistoryRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: stateTest_pb.HistoryResponse|null) => void
  ): UnaryResponse;
  history(
    requestMessage: stateTest_pb.HistoryRequest,
    callback: (error: ServiceError|null, responseMessage: stateTest_pb.HistoryResponse|null) => void
  ): UnaryResponse;
  change(
    requestMessage: stateTest_pb.ChangeRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  change(
    requestMessage: stateTest_pb.ChangeRequest,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  setPriority(
    requestMessage: stateTest_pb.SetPriorityRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  setPriority(
    requestMessage: stateTest_pb.SetPriorityRequest,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  setAvailability(
    requestMessage: stateTest_pb.SetAvailabilityRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  setAvailability(
    requestMessage: stateTest_pb.SetAvailabilityRequest,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  additionalInformation(
    requestMessage: stateTest_pb.AdditionalInformationRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  additionalInformation(
    requestMessage: stateTest_pb.AdditionalInformationRequest,
    callback: (error: ServiceError|null, responseMessage: google_protobuf_empty_pb.Empty|null) => void
  ): UnaryResponse;
  possibleNextStates(
    requestMessage: stateTest_pb.StandardRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: stateTest_pb.StatesResponse|null) => void
  ): UnaryResponse;
  possibleNextStates(
    requestMessage: stateTest_pb.StandardRequest,
    callback: (error: ServiceError|null, responseMessage: stateTest_pb.StatesResponse|null) => void
  ): UnaryResponse;
  getStatesByIds(
    requestMessage: stateTest_pb.StatesByIdRequest,
    metadata: grpc.Metadata,
    callback: (error: ServiceError|null, responseMessage: stateTest_pb.StatesByIdResponse|null) => void
  ): UnaryResponse;
  getStatesByIds(
    requestMessage: stateTest_pb.StatesByIdRequest,
    callback: (error: ServiceError|null, responseMessage: stateTest_pb.StatesByIdResponse|null) => void
  ): UnaryResponse;
}

