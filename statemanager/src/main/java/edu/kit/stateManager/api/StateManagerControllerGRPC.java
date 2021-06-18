package edu.kit.stateManager.api;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import edu.kit.stateManager.infrastructure.inputClasses.*;
import edu.kit.stateManager.infrastructure.outputClasses.AdditionalInformationOutput;
import edu.kit.stateManager.infrastructure.outputClasses.CurrentStateOutput;
import edu.kit.stateManager.infrastructure.outputClasses.StateOutput;
import edu.kit.stateManager.infrastructure.outputClasses.HistoryOutput;
import com.example.demo.grpc.*;
import edu.kit.stateManager.logic.model.state.DefectPriority;
import edu.kit.stateManager.logic.model.state.StateNames;
import edu.kit.stateManager.logic.service.StateHandler;
import edu.kit.stateManager.logic.service.exceptions.*;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.grpc.GRPCState;


import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@GrpcService
public class StateManagerControllerGRPC extends StateServiceGrpc.StateServiceImplBase {

    private final StateHandler stateHandler;

    @Autowired
    public StateManagerControllerGRPC(StateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }

    private void handleException (ErrorCode errorCode, Exception e, StreamObserver responseObserver) {
        Metadata metadata = new Metadata();
        Metadata.Key<ErrorResponse> responseKey = ProtoUtils.keyForProto(ErrorResponse.getDefaultInstance());
        ErrorResponse errorResponse = ErrorResponse.newBuilder()
                .setErrorCode(errorCode)
                .build();
        metadata.put(responseKey, errorResponse);
        responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException(metadata));

    }



    public void initialState(InitialStateRequest request, StreamObserver<Empty> responseObserver) {
        UUID userUUID = parseUUID(request.getUserUUID());
        UUID deviceUUID = parseUUID(request.getDeviceUUID());
        StateNames newState = toModelState(request.getNewState());

        try {
            stateHandler.addInitialState(new StateInput(deviceUUID, userUUID, newState));
        } catch (DeviceException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (IncorrectAttributeException e) {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

    }


    public void deleteHistory(StandardRequest request, StreamObserver<Empty> responseObserver) {
        UUID userUUID = parseUUID(request.getUserUUID());
        UUID deviceUUID = parseUUID(request.getDeviceUUID());

        try {
            stateHandler.deleteHistory(new DeleteInput(deviceUUID, userUUID));
        } catch (DeviceException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (IncorrectAttributeException e) {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (StateException e) {
            handleException(ErrorCode.STATE_EXCEPTION, e, responseObserver);
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }


    public void removeState(StandardRequest request, StreamObserver<Empty> responseObserver) {
        UUID userUUID = parseUUID(request.getUserUUID());
        UUID deviceUUID = parseUUID(request.getDeviceUUID());

        try {
            stateHandler.removeState(new DeleteInput(deviceUUID, userUUID));
        } catch (DeviceException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (IncorrectAttributeException e)  {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    public void state(StandardRequest request, StreamObserver<StateResponse> responseObserver) {

        UUID deviceUUID = parseUUID(request.getDeviceUUID()); //cast string to UUID
        CurrentStateOutput currentStateOutput;
        UUID userUUID = parseUUID(request.getUserUUID());
        try {
            currentStateOutput = stateHandler.getDeviceState(deviceUUID, userUUID); //get the device object
        } catch (DeviceException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (IncorrectAttributeException e) {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }


        responseObserver.onNext(buildStateResponse(currentStateOutput));
        responseObserver.onCompleted();

    }


    public void setPriority(SetPriorityRequest request, StreamObserver<Empty> responseObserver) {
        DefectPriority priority = toModelDefectPriority(request.getNewPriority());
        UUID userUUID = parseUUID(request.getUserUUID());
        UUID deviceUUID = parseUUID(request.getDeviceUUID());

        try {
            stateHandler.setPriority(new DefectPriorityInput(deviceUUID, userUUID, priority));
        } catch (DeviceException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (IncorrectAttributeException e) {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    public void change(ChangeRequest request, StreamObserver<Empty> responseObserver) {
        GRPCState state = request.getNewState();
        StateNames newState = toModelState(state);
        UUID userUUID = parseUUID(request.getUserUUID());
        UUID deviceUUID = parseUUID(request.getDeviceUUID());

        try {
            stateHandler.changeState(new StateInput(deviceUUID, userUUID, newState));
        } catch (DeviceException | StateException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (IncorrectAttributeException e) {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException | UserLevelException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

    }


    public void setAvailability(SetAvailabilityRequest request, StreamObserver<Empty> responseObserver) {
        Timestamp availabilityDate = request.getAvailabilityDate();
        UUID userUUID = parseUUID(request.getUserUUID());
        UUID deviceUUID = parseUUID(request.getDeviceUUID());
        LocalDate date = parseToLocalDate(availabilityDate);


        try {
            stateHandler.setAvailabilityDate(new AvailabilityDateInput(deviceUUID, userUUID, date));
        }catch (DeviceException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (IncorrectAttributeException e) {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (StateException e) {
            handleException(ErrorCode.STATE_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();


    }


    public void additionalInformation(AdditionalInformationRequest request, StreamObserver<Empty> responseObserver) {

        String additionalInformation = request.getAdditionalInformation();
        UUID userUUID = parseUUID(request.getUserUUID());
        UUID deviceUUID = parseUUID(request.getDeviceUUID());

        try {
            stateHandler.addAdditionalInformation(new AdditionalInformationInput(deviceUUID, userUUID, additionalInformation));
        } catch (DeviceException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (IncorrectAttributeException e) {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

    }


    public void history(HistoryRequest request, StreamObserver<HistoryResponse> responseObserver) {
        UUID deviceUUID = parseUUID(request.getDeviceUUID());
        UUID userUUID = parseUUID(request.getUserUUID());
        LocalDate from = parseToLocalDate(request.getFromDate());
        LocalDate to = parseToLocalDate(request.getToDate());

        HistoryOutput historyOutput;
        try {
            historyOutput = stateHandler.getStateHistory(new ShowHistoryInput(deviceUUID, userUUID, from, to));
        } catch (DeviceException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (IncorrectAttributeException e) {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }

        HistoryResponse.Builder responseBuilder = HistoryResponse.newBuilder();
        responseBuilder.setDeviceUUID(historyOutput.getDeviceId().toString());
        List<StateOutput> states = historyOutput.getStates();

        StateOutput stateOutput;
        StateOutputGRPC.Builder stateBuilder;
        List<AdditionalInformationOutput> additionalInformationOutputs;
        AdditionalInformationOutput additionalInformation;
        Instant enteredAt;
        Instant availabilityDate;
        Instant addedAt;
        int counter = states==null ? 0 : states.size();
        for(int i = 0; i < counter; i++) {
           stateOutput = states.get(i);
           stateBuilder = StateOutputGRPC.newBuilder();
           if (stateOutput.getAvailabilityDate() != null) {
               availabilityDate = stateOutput.getAvailabilityDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
               stateBuilder.setAvailabilityDate(Timestamp.newBuilder().setSeconds(availabilityDate.getEpochSecond())
                       .setNanos(availabilityDate.getNano()).build());
           }
           enteredAt = stateOutput.getEnteredAt().atZone(ZoneId.systemDefault()).toInstant();
           stateBuilder.setChangeTime(Timestamp.newBuilder().setSeconds(enteredAt.getEpochSecond())
                   .setNanos(enteredAt.getNano()).build());
           stateBuilder.setDeviceState(toGrpcState(stateOutput.getState()));
           stateBuilder.setEnteredByUserUUID(stateOutput.getEnteredBy().toString());
           additionalInformationOutputs = stateOutput.getAdditionalInformation();
           for (int j = 0; j < additionalInformationOutputs.size(); j++) {
               additionalInformation = additionalInformationOutputs.get(j);
               addedAt = additionalInformation.getTime().atZone(ZoneId.systemDefault()).toInstant();
               stateBuilder.addAdditionalInformation(
                       AdditionalInformationGRPC.newBuilder()
                               .setUserUUID(additionalInformation.getUser().toString())
                               .setInformation(additionalInformation.getInformation())
                               .setChangeTime(Timestamp.newBuilder().setSeconds(addedAt.getEpochSecond())
                                       .setNanos(addedAt.getNano()).build())
                               .build());

           }
           stateBuilder.build();
           responseBuilder.addChange(stateBuilder);
        }
        HistoryResponse response = responseBuilder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();


    }


    public void possibleNextStates(StandardRequest request, StreamObserver<StatesResponse> responseObserver) {
        UUID deviceUUID = parseUUID(request.getDeviceUUID());
        UUID userUUID = parseUUID(request.getUserUUID());
        List<StateNames> stateNames;
        try {
            stateNames = stateHandler.getPossibleNextStates(deviceUUID, userUUID);
        } catch (DeviceException e) {
            handleException(ErrorCode.DEVICE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }
        StatesResponse.Builder responseBuilder = StatesResponse.newBuilder();

        for (int i = 0; i < stateNames.size(); i++) {
            responseBuilder.addPossibleState(toGrpcState(stateNames.get(i)));
        }
        StatesResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }


    public void getStatesByIds(StatesByIdRequest request, StreamObserver<StatesByIdResponse> responseObserver) {
        UUID userUUID= parseUUID(request.getUserUUID());
        List<String> deviceList = request.getDeviceUUIDList();
        List<UUID> deviceUUIDs = new LinkedList<>();
        UUID[] ids = null;
        if (deviceList != null) {


            for (int i = 0; i < deviceList.size(); i++) {
                deviceUUIDs.add(parseUUID(deviceList.get(i)));
            }
            ids = deviceUUIDs.toArray(new UUID[0]);

        }
        List<GRPCState> grpcStates= request.getStateList();
        List<StateNames> states = null;
        if (grpcStates.size() != 0){
            states = new LinkedList<>();
            for (int i = 0; i < grpcStates.size(); i++){
                states.add(toModelState(grpcStates.get(i)));
            }
        }
        LocalDate fromDate = parseToLocalDate(request.getFromDate());
        LocalDate toDate = parseToLocalDate(request.getToDate());
        DefectPriority defectPriority= toModelDefectPriority(request.getDefectPriority());
        FilteredDeviceListInput input = new FilteredDeviceListInput(states, defectPriority, fromDate, toDate, ids, userUUID);

        List<CurrentStateOutput> currentStateOutputList;
        try {
            currentStateOutputList = stateHandler.getFilteredDevicesByID(input);
        } catch (IncorrectAttributeException e) {
            handleException(ErrorCode.INCORRECT_ATTRIBUTE_EXCEPTION, e, responseObserver);
            return;
        } catch (UserException e) {
            handleException(ErrorCode.USER_EXCEPTION, e, responseObserver);
            return;
        } catch (ConnectionException e) {
            handleException(ErrorCode.CONNECTION_EXCEPTION, e, responseObserver);
            return;
        }

        StatesByIdResponse.Builder responseBuilder = StatesByIdResponse.newBuilder();
        for (int i = 0; i < currentStateOutputList.size(); i++) {
            responseBuilder.addState(buildStateResponse(currentStateOutputList.get(i)));
        }
        StatesByIdResponse response= responseBuilder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();


    }

    private StateResponse buildStateResponse(CurrentStateOutput currentStateOutput){
        StateResponse.Builder responseBuilder = StateResponse.newBuilder();
        if(currentStateOutput.getDefectPriority() != null) {
            responseBuilder.setDefectPriority(toGRPCDefectPriority(currentStateOutput.getDefectPriority()));
        }
        if(currentStateOutput.getState() != null) {
            responseBuilder.setDeviceState(toGrpcState(currentStateOutput.getState()));
        }
        responseBuilder.setDeviceUUID(currentStateOutput.getDeviceId().toString());
        if (currentStateOutput.getEnteredBy() != null) {
            responseBuilder.setEnteredByUserUUID(currentStateOutput.getEnteredBy().toString());
        }
        if (currentStateOutput.getEnteredAt() != null) {
            Instant enteredAt = currentStateOutput.getEnteredAt().atZone(ZoneId.systemDefault()).toInstant();
            responseBuilder.setChangeTime(Timestamp.newBuilder().setSeconds(enteredAt.getEpochSecond()).setNanos(enteredAt.getNano()).build());
        }
        if (currentStateOutput.getAvailabilityDate() != null) {
            Instant availabilityDate = currentStateOutput.getAvailabilityDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            responseBuilder.setAvailabilityDate(Timestamp.newBuilder().setSeconds(availabilityDate.getEpochSecond())
                    .setNanos(availabilityDate.getNano()).build());
        }

        List<AdditionalInformationOutput> additionalInformationList =  currentStateOutput.getAdditionalInformation();
        AdditionalInformationOutput additionalInformation;
        int counter = additionalInformationList == null ? 0 : additionalInformationList.size();
        for(int i = 0; i < counter; i++) {
            additionalInformation = additionalInformationList.get(i);
            Instant addedAt = additionalInformation.getTime().atZone(ZoneId.systemDefault()).toInstant();
            responseBuilder.addAdditionalInformation(
                    AdditionalInformationGRPC.newBuilder()
                            .setChangeTime(Timestamp.newBuilder().setSeconds(addedAt.getEpochSecond()).setNanos(addedAt.getNano()).build())
                            .setInformation(additionalInformation.getInformation())
                            .setUserUUID(additionalInformation.getUser().toString())
                            .build());
        }

        StateResponse response = responseBuilder.build();
        return response;

    }

    private DefectPriority toModelDefectPriority(GRPCDefectPriority defectPriority) {
        DefectPriority newPriority;
        switch (defectPriority) {
            case LOW:
                newPriority = DefectPriority.LOW;
                break;
            case MEDIUM:
                newPriority = DefectPriority.MEDIUM;
                break;
            case HIGH:
                newPriority = DefectPriority.HIGH;
                break;
            default:
                newPriority = null;

        }
        return newPriority;
    }

    private GRPCDefectPriority toGRPCDefectPriority(DefectPriority defectPriority) {
        GRPCDefectPriority newPriority;
        if (defectPriority == null) {
            return null;
        }

        switch (defectPriority) {
            case LOW:
                newPriority = GRPCDefectPriority.LOW;
                break;
            case MEDIUM:
                newPriority = GRPCDefectPriority.MEDIUM;
                break;
            case HIGH:
                newPriority = GRPCDefectPriority.HIGH;
                break;
            default:
                newPriority = null;

        }
        return newPriority;
    }

    private StateNames toModelState(GRPCState state) {
        StateNames newState;

        switch (state){
            case BOUGHT:
                newState = StateNames.BOUGHT;
                break;
            case AVAILABLE:
                newState = StateNames.AVAILABLE;
                break;
            case RESERVED:
                newState = StateNames.RESERVED;
                break;
            case IN_USE:
                newState = StateNames.IN_USE;
                break;
            case CLEANING:
                newState = StateNames.CLEANING;
                break;
            case DEFECT_INSPECTION:
                newState = StateNames.DEFECT_INSPECTION;
                break;
            case MAINTENANCE:
                newState = StateNames.MAINTENANCE;
                break;
            case DEFECT_NO_REPAIR:
                newState = StateNames.DEFECT_NO_REPAIR;
                break;
            case DISPOSED:
                newState = StateNames.DISPOSED;
                break;
            case DEFECT_EXTERNAL_REPAIR:
                newState = StateNames.DEFECT_EXTERNAL_REPAIR;
                break;
            case DEFECT_OWN_REPAIR:
                newState = StateNames.DEFECT_OWN_REPAIR;
                break;
            default:
                newState = null;

        }
        return newState;
    }

    private GRPCState toGrpcState(StateNames state){
        GRPCState newState;

        switch (state){
            case NO_STATE:
                newState = GRPCState.NO_STATE;
                break;
            case BOUGHT:
                newState = GRPCState.BOUGHT;
                break;
            case AVAILABLE:
                newState = GRPCState.AVAILABLE;
                break;
            case RESERVED:
                newState = GRPCState.RESERVED;
                break;
            case IN_USE:
                newState = GRPCState.IN_USE;
                break;
            case CLEANING:
                newState = GRPCState.CLEANING;
                break;
            case DEFECT_INSPECTION:
                newState = GRPCState.DEFECT_INSPECTION;
                break;
            case MAINTENANCE:
                newState = GRPCState.MAINTENANCE;
                break;
            case DEFECT_NO_REPAIR:
                newState = GRPCState.DEFECT_NO_REPAIR;
                break;
            case DISPOSED:
                newState = GRPCState.DISPOSED;
                break;
            case DEFECT_EXTERNAL_REPAIR:
                newState = GRPCState.DEFECT_EXTERNAL_REPAIR;
                break;
            case DEFECT_OWN_REPAIR:
                newState = GRPCState.DEFECT_OWN_REPAIR;
                break;
            default:
                newState = null;

        }
        return newState;
    }

    private UUID parseUUID(String input) {
        UUID uuid = null;
        if (input != null) {
            try {
                uuid = UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return uuid;
    }

    private LocalDate parseToLocalDate(Timestamp timestamp) {
        if(timestamp.getDefaultInstanceForType().equals(timestamp)) {
            return null;
        }
        LocalDate date = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                .atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }
}
