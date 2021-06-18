// package: com.example.demo.grpc
// file: stateTest.proto

var stateTest_pb = require("./stateTest_pb");
var google_protobuf_empty_pb = require("google-protobuf/google/protobuf/empty_pb");
var grpc = require("@improbable-eng/grpc-web").grpc;

var StateService = (function () {
  function StateService() {}
  StateService.serviceName = "com.example.demo.grpc.StateService";
  return StateService;
}());

StateService.initialState = {
  methodName: "initialState",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.InitialStateRequest,
  responseType: google_protobuf_empty_pb.Empty
};

StateService.removeState = {
  methodName: "removeState",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.StandardRequest,
  responseType: google_protobuf_empty_pb.Empty
};

StateService.deleteHistory = {
  methodName: "deleteHistory",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.StandardRequest,
  responseType: google_protobuf_empty_pb.Empty
};

StateService.state = {
  methodName: "state",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.StandardRequest,
  responseType: stateTest_pb.StateResponse
};

StateService.history = {
  methodName: "history",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.HistoryRequest,
  responseType: stateTest_pb.HistoryResponse
};

StateService.change = {
  methodName: "change",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.ChangeRequest,
  responseType: google_protobuf_empty_pb.Empty
};

StateService.setPriority = {
  methodName: "setPriority",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.SetPriorityRequest,
  responseType: google_protobuf_empty_pb.Empty
};

StateService.setAvailability = {
  methodName: "setAvailability",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.SetAvailabilityRequest,
  responseType: google_protobuf_empty_pb.Empty
};

StateService.additionalInformation = {
  methodName: "additionalInformation",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.AdditionalInformationRequest,
  responseType: google_protobuf_empty_pb.Empty
};

StateService.possibleNextStates = {
  methodName: "possibleNextStates",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.StandardRequest,
  responseType: stateTest_pb.StatesResponse
};

StateService.getStatesByIds = {
  methodName: "getStatesByIds",
  service: StateService,
  requestStream: false,
  responseStream: false,
  requestType: stateTest_pb.StatesByIdRequest,
  responseType: stateTest_pb.StatesByIdResponse
};

exports.StateService = StateService;

function StateServiceClient(serviceHost, options) {
  this.serviceHost = serviceHost;
  this.options = options || {};
}

StateServiceClient.prototype.initialState = function initialState(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.initialState, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.removeState = function removeState(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.removeState, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.deleteHistory = function deleteHistory(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.deleteHistory, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.state = function state(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.state, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.history = function history(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.history, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.change = function change(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.change, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.setPriority = function setPriority(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.setPriority, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.setAvailability = function setAvailability(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.setAvailability, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.additionalInformation = function additionalInformation(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.additionalInformation, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.possibleNextStates = function possibleNextStates(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.possibleNextStates, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

StateServiceClient.prototype.getStatesByIds = function getStatesByIds(requestMessage, metadata, callback) {
  if (arguments.length === 2) {
    callback = arguments[1];
  }
  var client = grpc.unary(StateService.getStatesByIds, {
    request: requestMessage,
    host: this.serviceHost,
    metadata: metadata,
    transport: this.options.transport,
    debug: this.options.debug,
    onEnd: function (response) {
      if (callback) {
        if (response.status !== grpc.Code.OK) {
          var err = new Error(response.statusMessage);
          err.code = response.status;
          err.metadata = response.trailers;
          callback(err, null);
        } else {
          callback(null, response.message);
        }
      }
    }
  });
  return {
    cancel: function () {
      callback = null;
      client.close();
    }
  };
};

exports.StateServiceClient = StateServiceClient;

