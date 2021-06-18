package edu.kit.stateManager.logic.service;

import edu.kit.stateManager.infrastructure.converter.CommandConverter;
import edu.kit.stateManager.infrastructure.inputClasses.*;
import edu.kit.stateManager.infrastructure.outputClasses.AdditionalInformationOutput;
import edu.kit.stateManager.infrastructure.outputClasses.CurrentStateOutput;
import edu.kit.stateManager.infrastructure.outputClasses.HistoryOutput;
import edu.kit.stateManager.infrastructure.outputClasses.StateOutput;
import edu.kit.stateManager.infrastructure.converter.StateNameConverter;
import edu.kit.stateManager.infrastructure.dao.MedicalDeviceRepository;
import edu.kit.stateManager.infrastructure.dao.StateRepository;
import edu.kit.stateManager.logic.model.permission.Command;
import edu.kit.stateManager.logic.model.permission.CommandPermission;
import edu.kit.stateManager.logic.model.permission.StateTransitionPermission;
import edu.kit.stateManager.logic.model.permission.UserLevel;
import edu.kit.stateManager.logic.model.state.*;
import edu.kit.stateManager.logic.service.exceptions.*;
import edu.kit.stateManager.logic.service.external.MedicalDeviceClient;
import edu.kit.stateManager.logic.service.external.MedicalDeviceManager;
import edu.kit.stateManager.logic.service.external.ReservationClient;
import edu.kit.stateManager.logic.service.external.ReservationManager;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Transactional
public class StateHandler {

    StateNames DEFAULT_INITIAL_STATE = StateNames.AVAILABLE;
    DefectPriority INITIAL_PRIORITY = DefectPriority.LOW;

    private final MedicalDeviceRepository medicalDeviceRepository;
    private final StateRepository stateRepository;



    private final MedicalDeviceClient medicalDeviceClient;
    private final ReservationClient reservationClient;
    private final PermissionHandler permissionHandler;

    @Autowired
    public StateHandler(MedicalDeviceRepository medicalDeviceRepository, StateRepository stateRepository, PermissionHandler permissionHandler, ReservationManager reservationManager) {
        this.medicalDeviceRepository = medicalDeviceRepository;
        this.stateRepository = stateRepository;
        this.permissionHandler = permissionHandler;
        this.medicalDeviceClient = new MedicalDeviceManager();
        this.reservationClient = reservationManager;
    }

    public StateHandler(MedicalDeviceRepository medicalDeviceRepository, StateRepository stateRepository, MedicalDeviceClient medicalDeviceClient, PermissionHandler permissionHandler, ReservationManager reservationManager) {
        this.medicalDeviceRepository = medicalDeviceRepository;
        this.stateRepository = stateRepository;
        this.medicalDeviceClient = medicalDeviceClient;
        this.permissionHandler = permissionHandler;
        this.reservationClient = reservationManager;
    }

    /**
     * Saves the medical device UUID and the default priority (LOW) in the database and sets its current state.
     * For the state, the userId parameter is required, the state name is optional. If not given, it will be set with
     * the default value (AVAILABLE).
     * @param stateInput Input parameters:
     *                      required: deviceId, userId
     *                      optional: stateName
     * @return deviceId
     * @throws DeviceException If the given medical device already exists in the database
     * @throws IncorrectAttributeException If not all required attributes are given (deviceId and userId)
     * @throws UserException If the user does not exist or does not have the required permissions
     */
    public UUID addInitialState(StateInput stateInput) throws DeviceException, IncorrectAttributeException, UserException, ConnectionException {
        Command command = Command.ADD_INITIAL_STATE;
        if (!stateInput.hasRequiredInputInitialize()) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        if (!permissionHandler.canPerformCommand(command, stateInput.getUserId()).getValue()) {
            throw new UserException("User has no permission to add initial state");
        }
        BooleanResponse response = medicalDeviceClient.medicalDeviceExists(stateInput.getDeviceId());
        if (!response.getValue()) {
            throw new DeviceNotFoundException(response.getMessage());
        }
        Optional<MedicalDevice> device = medicalDeviceRepository.findByDeviceId(stateInput.getDeviceId());
        if (device.isPresent()) {
            MedicalDevice medicalDevice = device.get();
            if (!medicalDevice.getCurrentState().getState().equals(StateNames.NO_STATE)) {
                throw new DeviceException("Medical device already has a state");
            } else {
                State newState = new State(stateInput.getState(), stateInput.getUserId(), LocalDateTime.now());
                if (newState.getState() == null) {
                    newState.setState(DEFAULT_INITIAL_STATE);
                }
                State oldState = medicalDevice.getCurrentState();
                medicalDevice.setCurrentState(newState);
                medicalDevice.addToHistory(oldState);
                medicalDeviceRepository.saveAndFlush(medicalDevice);
                return stateInput.getDeviceId();
            }
        }
        State state = new State(stateInput.getState(), stateInput.getUserId(), LocalDateTime.now());
        if (stateInput.getState() == null) {
            state.setState(DEFAULT_INITIAL_STATE);
        } else {
            state.setState(stateInput.getState());
        }
        HistoryStatus history = new HistoryStatus();
        MedicalDevice medicalDevice = new MedicalDevice(stateInput.getDeviceId(), INITIAL_PRIORITY, state, history);
        medicalDeviceRepository.saveAndFlush(medicalDevice);
        return medicalDevice.getDeviceId();
    }

    /**
     * Removes the state of a medical device --> The state of the medical device gets changed to "noState"
     * @param removeInput Input parameters:
     *                       required: deviceId, userId
     * @return deviceId
     * @throws DeviceNotFoundException If the given medical device does not exist in the database
     * @throws UserException If the user does not exist or does not have the required permissions
     * @throws IncorrectAttributeException  If not all required attributes are given (deviceId and userId)
     */
    public UUID removeState(DeleteInput removeInput) throws DeviceNotFoundException, UserException, IncorrectAttributeException, ConnectionException {
        Command command = Command.REMOVE_STATE;
        if (!removeInput.hasRequiredInput()) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        if (!permissionHandler.canPerformCommand(command, removeInput.getUserId()).getValue()) {
            throw new UserException("User has no permission to remove the state of a medical device");
        }
        BooleanResponse response = medicalDeviceClient.medicalDeviceExists(removeInput.getDeviceId());
        if (!response.getValue()) {
            throw new DeviceNotFoundException(response.getMessage());
        }
        Optional<MedicalDevice> optional = medicalDeviceRepository.findByDeviceId(removeInput.getDeviceId());
        if (optional.isEmpty()) {
            throw new DeviceNotFoundException("Medical device has no state");
        }
        MedicalDevice medicalDevice = optional.get();
        if (medicalDevice.getCurrentState().getState().equals(StateNames.NO_STATE)) {
            throw new DeviceNotFoundException("Medical device has no state");
        }
        State newState = new State(StateNames.NO_STATE, removeInput.getUserId(), LocalDateTime.now());
        State oldState = medicalDevice.getCurrentState();
        medicalDevice.setCurrentState(newState);
        medicalDevice.addToHistory(oldState);
        medicalDeviceRepository.saveAndFlush(medicalDevice);
        return removeInput.getDeviceId();
    }

    /**
     * The entire history of the medical device gets deleted. This is only possible if the state of the device is "noState"
     * --> All database entries of the medical device get deleted
     * @param deleteInput Input parameters:
     *      *                required: deviceId, userId
     * @return deviceId
     * @throws DeviceNotFoundException If the given medical device does not exist in the database
     * @throws UserException If the user does not exist or does not have the required permissions
     * @throws IncorrectAttributeException  If not all required attributes are given (deviceId and userId)
     */
    public UUID deleteHistory(DeleteInput deleteInput) throws DeviceNotFoundException, UserException, IncorrectAttributeException, StateException, ConnectionException {
        Command command = Command.DELETE_HISTORY;
        if (!deleteInput.hasRequiredInput()) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        if (!permissionHandler.canPerformCommand(command, deleteInput.getUserId()).getValue()) {
            throw new UserException("User has no permission to delete medical device state");
        }
        Optional<MedicalDevice> optional = medicalDeviceRepository.findByDeviceId(deleteInput.getDeviceId());
        if (optional.isEmpty()) {
            throw new DeviceNotFoundException("No entries found matching the given device id");
        }
        MedicalDevice medicalDevice = optional.get();
        if (!medicalDevice.getCurrentState().getState().equals(StateNames.NO_STATE)) {
            throw new StateException("Medical device can not have a state when deleting its history");
        }
        medicalDeviceRepository.delete(medicalDevice);
        return deleteInput.getDeviceId();
    }

    /**
     * Changes the state of a medical device. This will assign the new current state (created by input parameters) to
     * the medical device and adds the past current state to the device history of this device.
     * @param stateInput Input parameters:
     *                      required: deviceId, userId, newStateName
     * @return deviceId
     * @throws DeviceNotFoundException If the given medical device does not exist in the database
     * @throws UserException If the user does not exist or does not have the required permissions
     * @throws IncorrectAttributeException If not all required attributes are given (deviceId, userId, newStateName)
     * @throws StateException If the current state is the same as the next state
     */
    public UUID changeState(StateInput stateInput) throws IncorrectAttributeException, DeviceNotFoundException, UserException, StateException, ConnectionException, UserLevelException {
        if (!stateInput.hasRequiredInput()) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        BooleanResponse responseDevice = medicalDeviceClient.medicalDeviceExists(stateInput.getDeviceId());
        if (!responseDevice.getValue()) {
            throw new DeviceNotFoundException(responseDevice.getMessage());
        }
        Optional<MedicalDevice> optional = medicalDeviceRepository.findByDeviceId(stateInput.getDeviceId());
        if (optional.isEmpty()) {
            throw new DeviceNotFoundException("Medical device has no state, initialize state first");
        }
        MedicalDevice medicalDevice = optional.get();
        if (medicalDevice.getCurrentState().getState().equals(stateInput.getState())) {
            throw new StateException("The current state is already " + stateInput.getState().toString());
        }
        State oldState = medicalDevice.getCurrentState();
        BooleanResponse responseTransition = permissionHandler.isValidStateTransition(medicalDevice.getCurrentState().getState(), stateInput.getState(), stateInput.getUserId());
        if (!responseTransition.getValue()) {
            throw new UserException(responseTransition.getMessage());
        }
        if (oldState.getState().equals(StateNames.RESERVED)) {
            if (!reservationClient.canChangeReservationStatus(stateInput.getDeviceId(), stateInput.getUserId())) {
                throw new UserException("User has not permission to change the state of this reservation");
            }
        }
        if (stateInput.getState().isDefectState()) {
            reservationClient.deviceDefect(stateInput.getDeviceId());
        }
        State newState = new State(stateInput.getState(), stateInput.getUserId(), LocalDateTime.now());
        medicalDevice.setCurrentState(newState);
        medicalDevice.addToHistory(oldState);
        medicalDeviceRepository.saveAndFlush(medicalDevice);
        return stateInput.getDeviceId();
    }

    /**
     * Changes the state of a medical device to reserved.
     * @param deviceId ID of the medical device
     * @param userId Id of the user
     * @throws DeviceNotFoundException Medical device does not exist in medical device microservice
     * @throws StateException conflict with current state of the medical device
     * @throws ConnectionException can not connect to required microservices
     * @throws UserException user hsa not permission to reserve a medical device
     * @return ID of the medical device
     */
    public UUID setToReserved(UUID deviceId, UUID userId) throws DeviceNotFoundException, StateException, ConnectionException, UserException {
        if (!permissionHandler.canPerformCommand(Command.RESERVE_DEVICE, userId).getValue()) {
            throw new UserException("User has no permission to reserve a medical device");
        }
        BooleanResponse responseDevice = medicalDeviceClient.medicalDeviceExists(deviceId);
        if (!responseDevice.getValue()) {
            throw new DeviceNotFoundException(responseDevice.getMessage());
        }
        Optional<MedicalDevice> optional = medicalDeviceRepository.findByDeviceId(deviceId);
        if (optional.isEmpty()) {
            throw new DeviceNotFoundException("Medical device has no state, initialize state first");
        }
        MedicalDevice medicalDevice = optional.get();
        if (!medicalDevice.getCurrentState().getState().equals(StateNames.AVAILABLE)) {
            throw new StateException("Can not change state from " + medicalDevice.getCurrentState().getState().toString() + " to RESERVED.");
        }
        State newState = new State(StateNames.RESERVED, userId, LocalDateTime.now());
        State oldState = medicalDevice.getCurrentState();
        medicalDevice.setCurrentState(newState);
        medicalDevice.addToHistory(oldState);
        medicalDeviceRepository.saveAndFlush(medicalDevice);
        return deviceId;
    }

    /**
     * Sets the defect priority of a medical device.
     * @param defectPriorityInput Input parameters:
     *                               required: deviceId, userId, newDefectPriority
     * @return deviceId
     * @throws UserException If the user does not exist or does not have the required permissions
     * @throws DeviceNotFoundException If the given medical device does not exist in the database
     * @throws IncorrectAttributeException If not all required attributes are given (deviceId, userId, newDefectPriority)
     */
    public UUID setPriority(DefectPriorityInput defectPriorityInput) throws IncorrectAttributeException, DeviceNotFoundException, UserException, ConnectionException {
        Command command = Command.SET_DEFECT_PRIORITY;
        if (!defectPriorityInput.hasRequiredInput()) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        if (!permissionHandler.canPerformCommand(command, defectPriorityInput.getUserId()).getValue()) {
            throw new UserException("User has no permission to set a defect priority");
        }
        BooleanResponse response = medicalDeviceClient.medicalDeviceExists(defectPriorityInput.getDeviceId());
        if (!response.getValue()) {
            throw new DeviceNotFoundException(response.getMessage());
        }
        Optional<MedicalDevice> optional = medicalDeviceRepository.findByDeviceId(defectPriorityInput.getDeviceId());
        if (optional.isEmpty()) {
            throw new DeviceNotFoundException("Medical device has no state, initialize state first");
        }
        MedicalDevice medicalDevice = optional.get();
        medicalDevice.setDefectPriority(defectPriorityInput.getDefectPriority());
        medicalDeviceRepository.saveAndFlush(medicalDevice);
        return defectPriorityInput.getDeviceId();
    }

    /**
     * Sets the availability date for a medical device. A availability date can only be set, if the current state of the
     * device an accept an availability date. The possible states are listed in StateNames.java.
     * @param availabilityDateInput Input parameters:
     *                                 required: deviceId, userId, newAvailabilityDate
     * @return deviceId
     * @throws UserException If the user does not exist or does not have the required permissions
     * @throws DeviceNotFoundException If the given medical device does not exist in the database
     * @throws IncorrectAttributeException If not all required attributes are given (deviceId, userId, newAvailabilityDate)
     * @throws StateException If the current state of the medical device does not accept an availability date
     */
    public UUID setAvailabilityDate(AvailabilityDateInput availabilityDateInput) throws IncorrectAttributeException, DeviceNotFoundException, UserException, StateException, ConnectionException {
        Command command = Command.SET_AVAILABILITY_DATE;
        if (!availabilityDateInput.hasRequiredInput()) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        if (!permissionHandler.canPerformCommand(command, availabilityDateInput.getUserId()).getValue()) {
            throw new UserException("User has no permission to add an availability date");
        }
        BooleanResponse response = medicalDeviceClient.medicalDeviceExists(availabilityDateInput.getDeviceId());
        if (!response.getValue()) {
            throw new DeviceNotFoundException(response.getMessage());
        }
        Optional<MedicalDevice> optional = medicalDeviceRepository.findByDeviceId(availabilityDateInput.getDeviceId());
        if (optional.isEmpty()) {
            throw new DeviceNotFoundException("Medical device has no state, initialize state first");
        }
        MedicalDevice medicalDevice = optional.get();
        State currentState = medicalDevice.getCurrentState();
        if (currentState.getState().hasAvailabilityDate()) {
            currentState.setAvailabilityDate(availabilityDateInput.getAvailabilityDate());
            medicalDeviceRepository.saveAndFlush(medicalDevice);
            return availabilityDateInput.getDeviceId();
        } else throw new StateException("This state can not have an availability date");

    }

    /**
     * Adds additional Information to the current state of a medical device.
     * @param informationInput Input parameters:
     *                            required: deviceId, userId, information
     * @return deviceId
     * @throws UserException If the user does not exist or does not have the required permissions
     * @throws DeviceNotFoundException If the given medical device does not exist in the database
     * @throws IncorrectAttributeException If not all required attributes are given (deviceId, userId, information)
     */
    public UUID addAdditionalInformation(AdditionalInformationInput informationInput) throws IncorrectAttributeException, DeviceNotFoundException, UserException, ConnectionException {
        Command command = Command.ADD_ADDITIONAL_INFORMATION;
        if (!informationInput.hasRequiredInput()) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        if (!permissionHandler.canPerformCommand(command, informationInput.getUserId()).getValue()) {
            throw new UserException("User has no permission to add additional information");
        }
        BooleanResponse response = medicalDeviceClient.medicalDeviceExists(informationInput.getDeviceId());
        if (!response.getValue()) {
            throw new DeviceNotFoundException(response.getMessage());
        }
        Optional<MedicalDevice> optional = medicalDeviceRepository.findByDeviceId(informationInput.getDeviceId());
        if (optional.isEmpty()) {
            throw new DeviceNotFoundException("Medical device has no state, initialize state first");
        }
        if (informationInput.getInformation().length() < 5) {
            throw new IncorrectAttributeException("Information needs to have at least 5 characters");
        }
        MedicalDevice medicalDevice = optional.get();
        State currentState = medicalDevice.getCurrentState();
        AdditionalInformation additionalInformation = new AdditionalInformation(informationInput.getUserId(), LocalDateTime.now(), currentState, informationInput.getInformation());
        currentState.addAdditionalInformation(additionalInformation);
        medicalDeviceRepository.saveAndFlush(medicalDevice);
        return informationInput.getDeviceId();
    }

    /**
     * Returns the current state of the medical device and the defect priority of the device.
     * @param deviceId Id of the medical device
     * @return Current state and defect priority of the device
     * @throws DeviceNotFoundException If the given medical device does not exist in the database
     */
    public CurrentStateOutput getDeviceState(UUID deviceId, UUID userId) throws DeviceNotFoundException, IncorrectAttributeException, UserException, ConnectionException {
        Command command = Command.GETTER;
        if (userId == null || deviceId == null) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        if (!permissionHandler.canPerformCommand(command, userId).getValue()) {
            throw new UserException("User has no permission to get device state");
        }
        BooleanResponse response = medicalDeviceClient.medicalDeviceExists(deviceId);
        if (!response.getValue()) {
            throw new DeviceNotFoundException(response.getMessage());
        }
        Optional<MedicalDevice> optional = medicalDeviceRepository.findByDeviceId(deviceId);
        if (optional.isEmpty()) {
            return new CurrentStateOutput(deviceId, null, StateNames.NO_STATE, null, null, null, null);
        }
        MedicalDevice medicalDevice = optional.get();
        StateOutput output = getStateOutput(medicalDevice.getCurrentState());
        return new CurrentStateOutput(medicalDevice.getDeviceId(), medicalDevice.getDefectPriority(), output.getState(), output.getEnteredBy(), output.getEnteredAt(), output.getAvailabilityDate(), output.getAdditionalInformation());
    }

    /**
     * Returns the state history of a medical device.
     * @param historyInput Input parameters:
     *                        required: deviceId, userId
     *                        optional: dateFrom, dateTo
     * @return state history of the medical device
     * @throws DeviceNotFoundException If the given medical device does not exist in the database
     * @throws UserException If the user does not exist or does not have the required permissions
     * @throws IncorrectAttributeException If there is an error with the given input
     */
    public HistoryOutput getStateHistory(ShowHistoryInput historyInput) throws DeviceNotFoundException, IncorrectAttributeException, UserException, ConnectionException {
        Command command = Command.GETTER;
        if (!historyInput.hasRequiredInput()) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        if (!permissionHandler.canPerformCommand(command, historyInput.getUserId()).getValue()) {
            throw new UserException("User has no permission to get device history");
        }
        BooleanResponse response = medicalDeviceClient.medicalDeviceExists(historyInput.getDeviceId());
        if (!response.getValue()) {
            throw new DeviceNotFoundException(response.getMessage());
        }
        Optional<MedicalDevice> device = medicalDeviceRepository.findByDeviceId(historyInput.getDeviceId());
        if (device.isEmpty()) {
            return new HistoryOutput(historyInput.getDeviceId(), null);
        }
        LocalDate from = historyInput.getFrom();
        LocalDate to = historyInput.getTo();
        LocalDate now = LocalDate.now();
        MedicalDevice medicalDevice = device.get();
        long historyId = medicalDevice.getHistoryStates().getId();
        List<State> allStates = new ArrayList<>();
        if (to == null && from == null) {   //return all history states
            allStates.addAll(stateRepository.findAllByHistoryIdOrderByEnteredAtDesc(historyId));
        } else if (to == null) {            //return all history states after date "from"
            if (from.isAfter(now)) {
                throw new IncorrectAttributeException("DateFrom is in the future");
            }
            LocalDateTime fromDateTime = LocalDateTime.of(from, LocalTime.MIN);
            allStates.addAll(stateRepository.findAllByEnteredAtAfterAndHistoryIdOrderByEnteredAtDesc(fromDateTime, historyId));
        } else if (from == null) {          //return all history states before date "to"
            if (to.isAfter(now)) {
                throw new IncorrectAttributeException("DateTo is in the future");
            }
            LocalDateTime toDateTime = LocalDateTime.of(to, LocalTime.MAX);
            allStates.addAll(stateRepository.findAllByEnteredAtBeforeAndHistoryIdOrderByEnteredAtDesc(toDateTime, historyId));
        } else {                            //return all history states between date "from" and date "to"
            if(from.isAfter(to))
                throw new  IncorrectAttributeException("DateFrom must be before dateTo");
            if (to.isAfter(now)) {
                throw new IncorrectAttributeException("DateTo is in the future");
            }
            if (from.isAfter(now)) {
                throw new IncorrectAttributeException("DateFrom is in the future");
            }
            LocalDateTime fromDateTime = LocalDateTime.of(from, LocalTime.MIN);
            LocalDateTime toDateTime = LocalDateTime.of(to, LocalTime.MAX);
            allStates.addAll(stateRepository.findAllByEnteredAtBetweenAndHistoryIdOrderByEnteredAtDesc(fromDateTime, toDateTime, historyId));
        }
        List<StateOutput> output = new ArrayList<>();
        for (State state: allStates) {
            output.add(getStateOutput(state));
        }
        return new HistoryOutput(medicalDevice.getDeviceId(), output);
    }

    /**
     * Returns the possible next states for the current state of the medical device and the user level of the user.
     * @param deviceId Id of the medical device
     * @param userId Id of the user
     * @return List of all possible next states
     * @throws DeviceNotFoundException If the medical device does not exist or has no state
     * @throws UserException If the user does not exist or does not have the required permissions
     */
    public List<StateNames> getPossibleNextStates(UUID deviceId, UUID userId) throws DeviceNotFoundException, UserException, ConnectionException {
        BooleanResponse response = medicalDeviceClient.medicalDeviceExists(deviceId);
        if (!response.getValue()) {
            throw new DeviceNotFoundException(response.getMessage());
        }
        Optional<MedicalDevice> optional = medicalDeviceRepository.findByDeviceId(deviceId);
        if (optional.isEmpty()) {
            throw new DeviceNotFoundException("Medical device has no state, initialize state first");
        }
        MedicalDevice medicalDevice = optional.get();
        if (medicalDevice.getCurrentState().getState().equals(StateNames.NO_STATE)) {
            throw new DeviceNotFoundException("Medical device has no state, initialize state first");
        }
        return permissionHandler.getPossibleNextStates(medicalDevice.getCurrentState().getState(), userId);
    }

    private StateOutput getStateOutput(State state) {
        List<AdditionalInformation> information = state.getAdditionalInformation();
        List<AdditionalInformationOutput> informationOutput = new ArrayList<>();
        for (AdditionalInformation additionalInformation:information) {
            informationOutput.add(getAdditionalInformationOutput(additionalInformation));
        }
        return new StateOutput(state.getState(), state.getEnteredBy(), state.getEnteredAt(), state.getAvailabilityDate(), informationOutput);
    }

    private AdditionalInformationOutput getAdditionalInformationOutput(AdditionalInformation information) {
        return new AdditionalInformationOutput(information.getUserId(), information.getAddedAt(), information.getInfo());
    }

    /**
     * Filters the gives devices depending on the filter input (state and priority).
     * @param filterInput Input parameters:
     *                       required: userId, deviceIds
     *                       optional: state, priority, from, to
     * @return Matching devices with their states
     * @throws IncorrectAttributeException If there is an error with the given input
     * @throws UserException If the user does not exist or does not have the required permissions
     */
    public List<CurrentStateOutput> getFilteredDevicesByID(FilteredDeviceListInput filterInput) throws IncorrectAttributeException, UserException, ConnectionException {
        Command command = Command.GETTER;
        if (!filterInput.hasRequiredInput()) {
            throw new IncorrectAttributeException("Not all attributes given");
        }
        if (!permissionHandler.canPerformCommand(command, filterInput.getUserId()).getValue()) {
            throw new UserException("User has no permission to get device history");
        }
        boolean filterStates = filterInput.getStates() != null;
        boolean filterPriority = filterInput.getDefectPriority() != null;
        boolean filterFrom = filterInput.getFrom() != null;
        boolean filterTo = filterInput.getTo() != null;
        if (filterFrom && filterTo) {
            if (filterInput.getFrom().isAfter(filterInput.getTo())) {
                throw new IncorrectAttributeException("DateFrom must be before dateTo");
            }
        }
        List<CurrentStateOutput> output = new ArrayList<>();
        for (UUID device: filterInput.getDevices()) {
            try {
                CurrentStateOutput currentDevice = getDeviceState(device, filterInput.getUserId());
                boolean add = true;
                if (filterStates) {
                    if (!filterInput.getStates().contains(currentDevice.getState())) add = false;
                }
                if (filterPriority) {
                    if (!filterInput.getDefectPriority().equals(currentDevice.getDefectPriority())) add = false;
                }
                if (filterFrom) {
                    if (currentDevice.getEnteredAt() == null) {
                        add = false;
                    } else if (!currentDevice.getEnteredAt().isAfter(LocalDateTime.of(filterInput.getFrom(), LocalTime.MIN))) add = false;
                }
                if (filterTo) {
                    if (currentDevice.getEnteredAt() == null) {
                        add = false;
                    } else if (!currentDevice.getEnteredAt().isBefore(LocalDateTime.of(filterInput.getTo(), LocalTime.MAX))) add = false;
                }
                if (add) output.add(currentDevice);
            } catch (DeviceNotFoundException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    /**
     * Sets the state transition permissions for one type with the given file
     * @param multipartFile file with the permission
     * @param userId Id of the user
     * @throws ConnectionException If the connection to staff manager is not possible
     * @throws IncorrectAttributeException If the attributes are not correct (in file)
     * @throws UserException If the user has not the needed permission (is not super user)
     */
    public String uploadStatePermissionFile(MultipartFile multipartFile, UUID userId) throws ConnectionException, IncorrectAttributeException, UserException {

        BooleanResponse response = permissionHandler.isSuperUser(userId);
        if (!response.getValue()) {
            throw new UserException(response.getMessage());
        }

        Workbook workbook = null;
        File file = null;
        InputStream inputStream;
        try {
            file = convert(multipartFile);
            inputStream = new FileInputStream(file);
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException | InvalidFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, StateNames> rowMapping = new HashMap<>();
        Map<Integer, StateNames> columnMapping = new HashMap<>();

        DataFormatter formatter = new DataFormatter();
        StateNameConverter stateNameConverter = new StateNameConverter();

        List<StateTransitionPermission> permissions = new ArrayList<>();

        String type = formatter.formatCellValue(sheet.getRow(0).getCell(0));

        if (!permissionHandler.isValidEmployeeType(type)) {
            closeFile(inputStream, file);
            throw new IncorrectAttributeException("The employee type " + type + " does not exist");
        }

        Row row0 = sheet.getRow(0);
        for (int i = 2; i < row0.getLastCellNum(); i++) {
            Cell cell = row0.getCell(i);
            String value = formatter.formatCellValue(cell);
            StateNames state;
            if (!value.equals("")) {
                try {
                    state = stateNameConverter.convertToEntityAttribute(value);
                    columnMapping.put(i, state);
                } catch (IllegalArgumentException e) {
                    closeFile(inputStream, file);
                    throw new IncorrectAttributeException("Illegal State name in row 1 column " + (i + 1) + ": " + value);
                }
            }
        }

        int iter = 2;
        Row row = sheet.getRow(iter);
        while (row != null) {
            Cell cell = row.getCell(0);
            String value = formatter.formatCellValue(cell);
            StateNames state;
            if (!value.equals("")) {
                try {
                    state = stateNameConverter.convertToEntityAttribute(value);
                    rowMapping.put(iter, state);
                } catch (IllegalArgumentException e) {
                    closeFile(inputStream, file);
                    throw new IncorrectAttributeException("Illegal State name in column 1 row " + (iter + 1) + ": " + value);
                }
            }
            iter++;
            row = sheet.getRow(iter);
        }

        for (int i = 2; i < iter; i++) {    //Iterates over rows
            for (int j = 2; j < row0.getLastCellNum(); j++) {   //Iterates over columns
                Cell cell = sheet.getRow(i).getCell(j);
                String minRank = formatter.formatCellValue(cell);
                if (!minRank.equals("")) {
                    if (!permissionHandler.isValidEmployeeRank(type, minRank)) {
                        closeFile(inputStream, file);
                        throw new IncorrectAttributeException("The rank " + minRank + " (row " + (i + 1) + ", column " + (j + 1) + " does not exist for type " + type);
                    }
                    if (rowMapping.get(i).equals(columnMapping.get(j))) {
                        closeFile(inputStream, file);
                        throw new IncorrectAttributeException("There should not be a permission from a state to the same state (row " + (i + 1) + ", column " + (j + 1) + ")");
                    }
                    permissions.add(new StateTransitionPermission(rowMapping.get(i), columnMapping.get(j), new UserLevel(type, minRank)));
                }
            }
        }

        permissionHandler.resetStatePermissionsForType(type, permissions);

        closeFile(inputStream, file);

        return type;
    }

    /**
     * Sets all command permission with the given file
     * @param multipartFile file with the command permissions
     * @param userId id of the user
     * @throws ConnectionException If the connection to staff manager is not possible
     * @throws IncorrectAttributeException If the attributes are not correct (in file)
     * @throws UserException If the user has not the needed permission (is not super user)
     */
    public void uploadCommandPermissionFile(MultipartFile multipartFile, UUID userId) throws ConnectionException, IncorrectAttributeException, UserException {

        BooleanResponse response = permissionHandler.isSuperUser(userId);
        if (!response.getValue()) {
            throw new UserException(response.getMessage());
        }

        Workbook workbook = null;
        File file = null;
        InputStream inputStream;
        try {
            file = convert(multipartFile);
            inputStream = new FileInputStream(file);
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException | InvalidFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();
        CommandConverter commandConverter = new CommandConverter();

        Map<Integer, Command> commandLines = new HashMap<>();

        Command currentCommand = null;
        int i = 2;
        Row row = sheet.getRow(i);
        while (row != null) {
            String commandName = formatter.formatCellValue(sheet.getRow(i).getCell(0));
            if (commandName.equals("")) {
                if (currentCommand != null) {
                    commandLines.put(i, currentCommand);
                }
            } else {
                Command command;
                try {
                    command = commandConverter.convertToEntityAttribute(commandName);
                    currentCommand = command;
                    commandLines.put(i, command);
                } catch (IllegalArgumentException e) {
                    closeFile(inputStream, file);
                    throw new IncorrectAttributeException("IllegalCommand name in column 1 row " + (i + 1) + ": " + commandName);
                }
            }
            i++;
            row = sheet.getRow(i);
        }

        List<CommandPermission> permissions = new ArrayList<>();

        i = 2;
        row = sheet.getRow(i);
        while (row != null) {
            String employeeType = formatter.formatCellValue(row.getCell(1));
            if (!employeeType.equals("")) {
                if (!permissionHandler.isValidEmployeeType(employeeType)) {
                    closeFile(inputStream, file);
                    throw new IncorrectAttributeException("Employee Type " +  employeeType + "(row " + (i + 1) + ", column 2) does not exist");
                }
                String employeeRank = formatter.formatCellValue(row.getCell(2));
                if (!permissionHandler.isValidEmployeeRank(employeeType, employeeRank)) {
                    closeFile(inputStream, file);
                    throw new IncorrectAttributeException("The rank " + employeeRank + " (row " + (i + 1) + ", column 3) does not exist for type " + employeeType);
                }
                Command command = commandLines.get(i);
                permissions.add(new CommandPermission(command, new UserLevel(employeeType, employeeRank)));
            }
            i++;
            row = sheet.getRow(i);
        }

        permissionHandler.resetCommandPermissions(permissions);

        closeFile(inputStream, file);
    }


    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void closeFile(InputStream inputStream, File file) {
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can not close workbook");
        }
        if (!file.delete()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not delete file");
        }
    }
}
