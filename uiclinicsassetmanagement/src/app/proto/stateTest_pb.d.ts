// package: com.example.demo.grpc
// file: stateTest.proto

import * as jspb from "google-protobuf";
import * as google_protobuf_timestamp_pb from "google-protobuf/google/protobuf/timestamp_pb";
import * as google_protobuf_empty_pb from "google-protobuf/google/protobuf/empty_pb";

export class StatesByIdRequest extends jspb.Message {
  clearDeviceuuidList(): void;
  getDeviceuuidList(): Array<string>;
  setDeviceuuidList(value: Array<string>): void;
  addDeviceuuid(value: string, index?: number): string;

  getUseruuid(): string;
  setUseruuid(value: string): void;

  clearStateList(): void;
  getStateList(): Array<GRPCStateMap[keyof GRPCStateMap]>;
  setStateList(value: Array<GRPCStateMap[keyof GRPCStateMap]>): void;
  addState(value: GRPCStateMap[keyof GRPCStateMap], index?: number): GRPCStateMap[keyof GRPCStateMap];

  hasFromdate(): boolean;
  clearFromdate(): void;
  getFromdate(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setFromdate(value?: google_protobuf_timestamp_pb.Timestamp): void;

  hasTodate(): boolean;
  clearTodate(): void;
  getTodate(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setTodate(value?: google_protobuf_timestamp_pb.Timestamp): void;

  getDefectpriority(): GRPCDefectPriorityMap[keyof GRPCDefectPriorityMap];
  setDefectpriority(value: GRPCDefectPriorityMap[keyof GRPCDefectPriorityMap]): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): StatesByIdRequest.AsObject;
  static toObject(includeInstance: boolean, msg: StatesByIdRequest): StatesByIdRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: StatesByIdRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): StatesByIdRequest;
  static deserializeBinaryFromReader(message: StatesByIdRequest, reader: jspb.BinaryReader): StatesByIdRequest;
}

export namespace StatesByIdRequest {
  export type AsObject = {
    deviceuuidList: Array<string>,
    useruuid: string,
    stateList: Array<GRPCStateMap[keyof GRPCStateMap]>,
    fromdate?: google_protobuf_timestamp_pb.Timestamp.AsObject,
    todate?: google_protobuf_timestamp_pb.Timestamp.AsObject,
    defectpriority: GRPCDefectPriorityMap[keyof GRPCDefectPriorityMap],
  }
}

export class StatesByIdResponse extends jspb.Message {
  clearStateList(): void;
  getStateList(): Array<StateResponse>;
  setStateList(value: Array<StateResponse>): void;
  addState(value?: StateResponse, index?: number): StateResponse;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): StatesByIdResponse.AsObject;
  static toObject(includeInstance: boolean, msg: StatesByIdResponse): StatesByIdResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: StatesByIdResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): StatesByIdResponse;
  static deserializeBinaryFromReader(message: StatesByIdResponse, reader: jspb.BinaryReader): StatesByIdResponse;
}

export namespace StatesByIdResponse {
  export type AsObject = {
    stateList: Array<StateResponse.AsObject>,
  }
}

export class StandardRequest extends jspb.Message {
  getDeviceuuid(): string;
  setDeviceuuid(value: string): void;

  getUseruuid(): string;
  setUseruuid(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): StandardRequest.AsObject;
  static toObject(includeInstance: boolean, msg: StandardRequest): StandardRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: StandardRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): StandardRequest;
  static deserializeBinaryFromReader(message: StandardRequest, reader: jspb.BinaryReader): StandardRequest;
}

export namespace StandardRequest {
  export type AsObject = {
    deviceuuid: string,
    useruuid: string,
  }
}

export class InitialStateRequest extends jspb.Message {
  getDeviceuuid(): string;
  setDeviceuuid(value: string): void;

  getUseruuid(): string;
  setUseruuid(value: string): void;

  getNewstate(): GRPCStateMap[keyof GRPCStateMap];
  setNewstate(value: GRPCStateMap[keyof GRPCStateMap]): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): InitialStateRequest.AsObject;
  static toObject(includeInstance: boolean, msg: InitialStateRequest): InitialStateRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: InitialStateRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): InitialStateRequest;
  static deserializeBinaryFromReader(message: InitialStateRequest, reader: jspb.BinaryReader): InitialStateRequest;
}

export namespace InitialStateRequest {
  export type AsObject = {
    deviceuuid: string,
    useruuid: string,
    newstate: GRPCStateMap[keyof GRPCStateMap],
  }
}

export class StateResponse extends jspb.Message {
  getDeviceuuid(): string;
  setDeviceuuid(value: string): void;

  getDefectpriority(): GRPCDefectPriorityMap[keyof GRPCDefectPriorityMap];
  setDefectpriority(value: GRPCDefectPriorityMap[keyof GRPCDefectPriorityMap]): void;

  getDevicestate(): GRPCStateMap[keyof GRPCStateMap];
  setDevicestate(value: GRPCStateMap[keyof GRPCStateMap]): void;

  getEnteredbyuseruuid(): string;
  setEnteredbyuseruuid(value: string): void;

  hasChangetime(): boolean;
  clearChangetime(): void;
  getChangetime(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setChangetime(value?: google_protobuf_timestamp_pb.Timestamp): void;

  hasAvailabilitydate(): boolean;
  clearAvailabilitydate(): void;
  getAvailabilitydate(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setAvailabilitydate(value?: google_protobuf_timestamp_pb.Timestamp): void;

  clearAdditionalinformationList(): void;
  getAdditionalinformationList(): Array<AdditionalInformationGRPC>;
  setAdditionalinformationList(value: Array<AdditionalInformationGRPC>): void;
  addAdditionalinformation(value?: AdditionalInformationGRPC, index?: number): AdditionalInformationGRPC;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): StateResponse.AsObject;
  static toObject(includeInstance: boolean, msg: StateResponse): StateResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: StateResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): StateResponse;
  static deserializeBinaryFromReader(message: StateResponse, reader: jspb.BinaryReader): StateResponse;
}

export namespace StateResponse {
  export type AsObject = {
    deviceuuid: string,
    defectpriority: GRPCDefectPriorityMap[keyof GRPCDefectPriorityMap],
    devicestate: GRPCStateMap[keyof GRPCStateMap],
    enteredbyuseruuid: string,
    changetime?: google_protobuf_timestamp_pb.Timestamp.AsObject,
    availabilitydate?: google_protobuf_timestamp_pb.Timestamp.AsObject,
    additionalinformationList: Array<AdditionalInformationGRPC.AsObject>,
  }
}

export class HistoryRequest extends jspb.Message {
  getDeviceuuid(): string;
  setDeviceuuid(value: string): void;

  getUseruuid(): string;
  setUseruuid(value: string): void;

  hasFromdate(): boolean;
  clearFromdate(): void;
  getFromdate(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setFromdate(value?: google_protobuf_timestamp_pb.Timestamp): void;

  hasTodate(): boolean;
  clearTodate(): void;
  getTodate(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setTodate(value?: google_protobuf_timestamp_pb.Timestamp): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): HistoryRequest.AsObject;
  static toObject(includeInstance: boolean, msg: HistoryRequest): HistoryRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: HistoryRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): HistoryRequest;
  static deserializeBinaryFromReader(message: HistoryRequest, reader: jspb.BinaryReader): HistoryRequest;
}

export namespace HistoryRequest {
  export type AsObject = {
    deviceuuid: string,
    useruuid: string,
    fromdate?: google_protobuf_timestamp_pb.Timestamp.AsObject,
    todate?: google_protobuf_timestamp_pb.Timestamp.AsObject,
  }
}

export class HistoryResponse extends jspb.Message {
  getDeviceuuid(): string;
  setDeviceuuid(value: string): void;

  clearChangeList(): void;
  getChangeList(): Array<StateOutputGRPC>;
  setChangeList(value: Array<StateOutputGRPC>): void;
  addChange(value?: StateOutputGRPC, index?: number): StateOutputGRPC;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): HistoryResponse.AsObject;
  static toObject(includeInstance: boolean, msg: HistoryResponse): HistoryResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: HistoryResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): HistoryResponse;
  static deserializeBinaryFromReader(message: HistoryResponse, reader: jspb.BinaryReader): HistoryResponse;
}

export namespace HistoryResponse {
  export type AsObject = {
    deviceuuid: string,
    changeList: Array<StateOutputGRPC.AsObject>,
  }
}

export class AdditionalInformationGRPC extends jspb.Message {
  getUseruuid(): string;
  setUseruuid(value: string): void;

  hasChangetime(): boolean;
  clearChangetime(): void;
  getChangetime(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setChangetime(value?: google_protobuf_timestamp_pb.Timestamp): void;

  getInformation(): string;
  setInformation(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AdditionalInformationGRPC.AsObject;
  static toObject(includeInstance: boolean, msg: AdditionalInformationGRPC): AdditionalInformationGRPC.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AdditionalInformationGRPC, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AdditionalInformationGRPC;
  static deserializeBinaryFromReader(message: AdditionalInformationGRPC, reader: jspb.BinaryReader): AdditionalInformationGRPC;
}

export namespace AdditionalInformationGRPC {
  export type AsObject = {
    useruuid: string,
    changetime?: google_protobuf_timestamp_pb.Timestamp.AsObject,
    information: string,
  }
}

export class StateOutputGRPC extends jspb.Message {
  getDevicestate(): GRPCStateMap[keyof GRPCStateMap];
  setDevicestate(value: GRPCStateMap[keyof GRPCStateMap]): void;

  getEnteredbyuseruuid(): string;
  setEnteredbyuseruuid(value: string): void;

  hasChangetime(): boolean;
  clearChangetime(): void;
  getChangetime(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setChangetime(value?: google_protobuf_timestamp_pb.Timestamp): void;

  hasAvailabilitydate(): boolean;
  clearAvailabilitydate(): void;
  getAvailabilitydate(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setAvailabilitydate(value?: google_protobuf_timestamp_pb.Timestamp): void;

  clearAdditionalinformationList(): void;
  getAdditionalinformationList(): Array<AdditionalInformationGRPC>;
  setAdditionalinformationList(value: Array<AdditionalInformationGRPC>): void;
  addAdditionalinformation(value?: AdditionalInformationGRPC, index?: number): AdditionalInformationGRPC;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): StateOutputGRPC.AsObject;
  static toObject(includeInstance: boolean, msg: StateOutputGRPC): StateOutputGRPC.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: StateOutputGRPC, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): StateOutputGRPC;
  static deserializeBinaryFromReader(message: StateOutputGRPC, reader: jspb.BinaryReader): StateOutputGRPC;
}

export namespace StateOutputGRPC {
  export type AsObject = {
    devicestate: GRPCStateMap[keyof GRPCStateMap],
    enteredbyuseruuid: string,
    changetime?: google_protobuf_timestamp_pb.Timestamp.AsObject,
    availabilitydate?: google_protobuf_timestamp_pb.Timestamp.AsObject,
    additionalinformationList: Array<AdditionalInformationGRPC.AsObject>,
  }
}

export class ChangeRequest extends jspb.Message {
  getDeviceuuid(): string;
  setDeviceuuid(value: string): void;

  getUseruuid(): string;
  setUseruuid(value: string): void;

  getNewstate(): GRPCStateMap[keyof GRPCStateMap];
  setNewstate(value: GRPCStateMap[keyof GRPCStateMap]): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ChangeRequest.AsObject;
  static toObject(includeInstance: boolean, msg: ChangeRequest): ChangeRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: ChangeRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ChangeRequest;
  static deserializeBinaryFromReader(message: ChangeRequest, reader: jspb.BinaryReader): ChangeRequest;
}

export namespace ChangeRequest {
  export type AsObject = {
    deviceuuid: string,
    useruuid: string,
    newstate: GRPCStateMap[keyof GRPCStateMap],
  }
}

export class SetPriorityRequest extends jspb.Message {
  getUseruuid(): string;
  setUseruuid(value: string): void;

  getDeviceuuid(): string;
  setDeviceuuid(value: string): void;

  getNewpriority(): GRPCDefectPriorityMap[keyof GRPCDefectPriorityMap];
  setNewpriority(value: GRPCDefectPriorityMap[keyof GRPCDefectPriorityMap]): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): SetPriorityRequest.AsObject;
  static toObject(includeInstance: boolean, msg: SetPriorityRequest): SetPriorityRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: SetPriorityRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): SetPriorityRequest;
  static deserializeBinaryFromReader(message: SetPriorityRequest, reader: jspb.BinaryReader): SetPriorityRequest;
}

export namespace SetPriorityRequest {
  export type AsObject = {
    useruuid: string,
    deviceuuid: string,
    newpriority: GRPCDefectPriorityMap[keyof GRPCDefectPriorityMap],
  }
}

export class SetAvailabilityRequest extends jspb.Message {
  getUseruuid(): string;
  setUseruuid(value: string): void;

  getDeviceuuid(): string;
  setDeviceuuid(value: string): void;

  hasAvailabilitydate(): boolean;
  clearAvailabilitydate(): void;
  getAvailabilitydate(): google_protobuf_timestamp_pb.Timestamp | undefined;
  setAvailabilitydate(value?: google_protobuf_timestamp_pb.Timestamp): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): SetAvailabilityRequest.AsObject;
  static toObject(includeInstance: boolean, msg: SetAvailabilityRequest): SetAvailabilityRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: SetAvailabilityRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): SetAvailabilityRequest;
  static deserializeBinaryFromReader(message: SetAvailabilityRequest, reader: jspb.BinaryReader): SetAvailabilityRequest;
}

export namespace SetAvailabilityRequest {
  export type AsObject = {
    useruuid: string,
    deviceuuid: string,
    availabilitydate?: google_protobuf_timestamp_pb.Timestamp.AsObject,
  }
}

export class AdditionalInformationRequest extends jspb.Message {
  getUseruuid(): string;
  setUseruuid(value: string): void;

  getDeviceuuid(): string;
  setDeviceuuid(value: string): void;

  getAdditionalinformation(): string;
  setAdditionalinformation(value: string): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): AdditionalInformationRequest.AsObject;
  static toObject(includeInstance: boolean, msg: AdditionalInformationRequest): AdditionalInformationRequest.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: AdditionalInformationRequest, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): AdditionalInformationRequest;
  static deserializeBinaryFromReader(message: AdditionalInformationRequest, reader: jspb.BinaryReader): AdditionalInformationRequest;
}

export namespace AdditionalInformationRequest {
  export type AsObject = {
    useruuid: string,
    deviceuuid: string,
    additionalinformation: string,
  }
}

export class StatesResponse extends jspb.Message {
  clearPossiblestateList(): void;
  getPossiblestateList(): Array<GRPCStateMap[keyof GRPCStateMap]>;
  setPossiblestateList(value: Array<GRPCStateMap[keyof GRPCStateMap]>): void;
  addPossiblestate(value: GRPCStateMap[keyof GRPCStateMap], index?: number): GRPCStateMap[keyof GRPCStateMap];

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): StatesResponse.AsObject;
  static toObject(includeInstance: boolean, msg: StatesResponse): StatesResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: StatesResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): StatesResponse;
  static deserializeBinaryFromReader(message: StatesResponse, reader: jspb.BinaryReader): StatesResponse;
}

export namespace StatesResponse {
  export type AsObject = {
    possiblestateList: Array<GRPCStateMap[keyof GRPCStateMap]>,
  }
}

export class ErrorResponse extends jspb.Message {
  getErrorcode(): ErrorCodeMap[keyof ErrorCodeMap];
  setErrorcode(value: ErrorCodeMap[keyof ErrorCodeMap]): void;

  serializeBinary(): Uint8Array;
  toObject(includeInstance?: boolean): ErrorResponse.AsObject;
  static toObject(includeInstance: boolean, msg: ErrorResponse): ErrorResponse.AsObject;
  static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
  static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
  static serializeBinaryToWriter(message: ErrorResponse, writer: jspb.BinaryWriter): void;
  static deserializeBinary(bytes: Uint8Array): ErrorResponse;
  static deserializeBinaryFromReader(message: ErrorResponse, reader: jspb.BinaryReader): ErrorResponse;
}

export namespace ErrorResponse {
  export type AsObject = {
    errorcode: ErrorCodeMap[keyof ErrorCodeMap],
  }
}

export interface GRPCStateMap {
  NO_STATE: 0;
  BOUGHT: 1;
  AVAILABLE: 2;
  RESERVED: 3;
  IN_USE: 4;
  CLEANING: 5;
  DEFECT_INSPECTION: 6;
  MAINTENANCE: 7;
  DEFECT_NO_REPAIR: 8;
  DISPOSED: 9;
  DEFECT_EXTERNAL_REPAIR: 10;
  DEFECT_OWN_REPAIR: 11;
}

export const GRPCState: GRPCStateMap;

export interface GRPCDefectPriorityMap {
  NO_PRIORITY: 0;
  LOW: 1;
  MEDIUM: 2;
  HIGH: 3;
}

export const GRPCDefectPriority: GRPCDefectPriorityMap;

export interface ErrorCodeMap {
  UNSPECIFIED_EXCEPTION: 0;
  INCORRECT_ATTRIBUTE_EXCEPTION: 1;
  STATE_EXCEPTION: 2;
  USER_EXCEPTION: 3;
  DEVICE_EXCEPTION: 4;
}

export const ErrorCode: ErrorCodeMap;

