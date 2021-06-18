package edu.kit.stateManager.logic.service;

import edu.kit.stateManager.infrastructure.inputClasses.DeleteInput;
import edu.kit.stateManager.infrastructure.inputClasses.StateInput;
import edu.kit.stateManager.infrastructure.dao.CommandPermissionRepository;
import edu.kit.stateManager.infrastructure.dao.MedicalDeviceRepository;
import edu.kit.stateManager.infrastructure.dao.StateRepository;
import edu.kit.stateManager.infrastructure.dao.StateTransitionPermissionRepository;
import edu.kit.stateManager.logic.model.state.HistoryStatus;
import edu.kit.stateManager.logic.model.state.MedicalDevice;
import edu.kit.stateManager.logic.model.state.State;
import edu.kit.stateManager.logic.model.state.StateNames;
import edu.kit.stateManager.logic.model.permission.Command;
import edu.kit.stateManager.logic.model.permission.CommandPermission;
import edu.kit.stateManager.logic.model.permission.UserLevel;
import edu.kit.stateManager.logic.service.exceptions.*;
import edu.kit.stateManager.logic.service.external.MedicalDeviceClient;
import edu.kit.stateManager.logic.service.external.StaffManager;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StateHandlerTest {

    StateRepository stateRepository = mock(StateRepository.class);
    MedicalDeviceRepository medicalDeviceRepository = mock(MedicalDeviceRepository.class);
    MedicalDeviceClient medicalDeviceClient = mock(MedicalDeviceClient.class);

    CommandPermissionRepository commandPermissionRepository = mock(CommandPermissionRepository.class);
    StateTransitionPermissionRepository stateTransitionPermissionRepository = mock(StateTransitionPermissionRepository.class);
    StaffManager staffManager = mock(StaffManager.class);

    PermissionHandler permissionHandler = new PermissionHandler(commandPermissionRepository, stateTransitionPermissionRepository, staffManager);

    UUID user = UUID.fromString("d8127e3c-4173-49d7-918f-6d59bdd93078");

    UUID medicalDevice = UUID.fromString("5dd8f62a-d4f8-440a-b317-b1ff69d6b76d");

//    /**
//     * addInitialState with user level TECHNICAL, HIGH works because the medical device exists in the medical device
//     * database and does not exist in the state database yet. (New state: AVAILABLE)
//     */
//    @Test
//    void addInitialStateSuccessAvailable() throws ConnectionException, UserException {
//
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.ADD_INITIAL_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.ADD_INITIAL_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//        try {
//            assertEquals(stateHandler.addInitialState(new StateInput(medicalDevice, user, StateNames.AVAILABLE)), medicalDevice);
//        } catch (DeviceException | IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).saveAndFlush(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//        assertEquals(device.getCurrentState().getState(), StateNames.AVAILABLE);
//    }
//
//    /**
//     * addInitialState with user level TECHNICAL, HIGH works because the medical device exists in the medical device
//     * database and does not exist in the state database yet. (New state: BOUGHT)
//     */
//    @Test
//    void addInitialStateSuccessBought() throws ConnectionException, UserException {
//
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.ADD_INITIAL_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.ADD_INITIAL_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//        try {
//            assertEquals(stateHandler.addInitialState(new StateInput(medicalDevice, user, StateNames.BOUGHT)), medicalDevice);
//        } catch (DeviceException | IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).saveAndFlush(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//        assertEquals(device.getCurrentState().getState(), StateNames.BOUGHT);
//    }
//
//    /**
//     * addInitialState with user level TECHNICAL, HIGH works because the medical device exists in the medical device
//     * database and has state "NO_STATE" in the state database. (New state: AVAILABLE)
//     */
//    @Test
//    void addInitialStateSuccessAvailableNoState() throws ConnectionException, UserException {
//
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.ADD_INITIAL_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.ADD_INITIAL_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.NO_STATE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        try {
//            assertEquals(stateHandler.addInitialState(new StateInput(medicalDevice, user, StateNames.AVAILABLE)), medicalDevice);
//        } catch (DeviceException | IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).saveAndFlush(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//        assertEquals(device.getCurrentState().getState(), StateNames.AVAILABLE);
//    }
//
//    /**
//     * addInitialState does not work because the user level is not sufficient.
//     */
//    @Test
//    void addInitialStateErrorInsufficientUserLevel() throws ConnectionException, UserException {
//
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "LOW");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.ADD_INITIAL_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(false);
//        when(commandPermissionRepository.findByCommandAndType(Command.ADD_INITIAL_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//
//        assertThrows(UserException.class, () -> {
//            stateHandler.addInitialState(new StateInput(medicalDevice, user, StateNames.AVAILABLE));
//        });
//    }
//
//    /**
//     * addInitialState does not work because the medical device already exists in the state database with a state which
//     * is not "NO_STATE".
//     */
//    @Test
//    void addInitialStateErrorDeviceExistsInDB() throws ConnectionException, UserException {
//
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.ADD_INITIAL_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.ADD_INITIAL_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.AVAILABLE, null, LocalDateTime.now()), new HistoryStatus())));
//
//
//        assertThrows(DeviceException.class, () -> {
//            stateHandler.addInitialState(new StateInput(medicalDevice, user, StateNames.AVAILABLE));
//        });
//    }
//
//    /**
//     * addInitialState does not work because the medical device does not exists in the medical device database
//     */
//    @Test
//    void addInitialStateErrorDeviceNotInMdDB() throws ConnectionException, UserException {
//
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.ADD_INITIAL_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.ADD_INITIAL_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(false, "The medical device does not exist in MedicalDevice Database"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.addInitialState(new StateInput(medicalDevice, user, StateNames.AVAILABLE));
//        });
//    }
//
//    /**
//     * removeState works with user level TECHNICAL HIGH because it exists in the medical device database and has a state
//     * in the state database
//     */
//    @Test
//    void removeStateSuccess() throws ConnectionException, UserException {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.REMOVE_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.REMOVE_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.AVAILABLE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        try {
//            stateHandler.removeState(new DeleteInput(medicalDevice, user));
//        } catch (DeviceException | IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).saveAndFlush(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//        assertEquals(device.getCurrentState().getState(), StateNames.NO_STATE);
//    }
//
//    /**
//     * removeState does not work because user level is too low (MEDICAL, HIGH).
//     */
//    @Test
//    void removeStateErrorUserLevel() throws ConnectionException, UserException {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "LOW");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.REMOVE_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(false);
//        when(commandPermissionRepository.findByCommandAndType(Command.REMOVE_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.AVAILABLE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(UserException.class, () -> {
//            stateHandler.removeState(new DeleteInput(medicalDevice, user));
//        });
//    }
//
//    /**
//     * removeState does not work because the medical device is not present in state database.
//     */
//    @Test
//    void removeStateErrorDeviceNotInOwnDB() throws ConnectionException, UserException {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.REMOVE_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.REMOVE_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.removeState(new DeleteInput(medicalDevice, user));
//        });
//    }
//
//    /**
//     * removeState does not work because the medical device already has the state "NO_STATE".
//     */
//    @Test
//    void removeStateErrorDeviceNoState() throws ConnectionException, UserException {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.REMOVE_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.REMOVE_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.NO_STATE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.removeState(new DeleteInput(medicalDevice, user));
//        });
//    }
//
//    /**
//     * removeState does not work because the medical device does not exist in the medical device database.
//     */
//    @Test
//    void removeStateErrorDeviceNotInMdDB() throws ConnectionException, UserException {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.REMOVE_STATE, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.REMOVE_STATE, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(false, "Does not exist"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.AVAILABLE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.removeState(new DeleteInput(medicalDevice, user));
//        });
//    }
//
//    @Test
//    void deleteHistorySuccess() throws ConnectionException, UserException {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "ADMIN");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.DELETE_HISTORY, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(true);
//        when(commandPermissionRepository.findByCommandAndType(Command.DELETE_HISTORY, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.NO_STATE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        try {
//            stateHandler.deleteHistory(new DeleteInput(medicalDevice, user));
//        } catch (DeviceException | IncorrectAttributeException | UserException | StateException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).delete(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//    }
//
//    /**
//     * deleteHistory does not work because user level is too low (MEDICAL, HIGH).
//     */
//    @Test
//    void deleteHistoryErrorUserLevel() throws ConnectionException, UserException {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        UserLevel userLevel = new UserLevel("TECHNICAL", "LOW");
//        UserLevel minUserLevel = new UserLevel("TECHNICAL", "NORMAL");
//        Optional<CommandPermission> commandPermission = Optional.of(new CommandPermission(Command.DELETE_HISTORY, minUserLevel));
//
//        when(staffManager.getUserLevel(user)).thenReturn(userLevel);
//        when(staffManager.employeeTypeExists(userLevel.getEmployeeType())).thenReturn(true);
//        when(staffManager.employeeRankExists(userLevel)).thenReturn(true);
//        //TODO: Can not get it working for minUserLevel and userLevel
//        when(staffManager.rankHigherEqualThan(any(), any())).thenReturn(false);
//        when(commandPermissionRepository.findByCommandAndType(Command.DELETE_HISTORY, userLevel.getEmployeeType())).thenReturn(commandPermission);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.NO_STATE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(UserException.class, () -> {
//            stateHandler.deleteHistory(new DeleteInput(medicalDevice, user));
//        });
//    }

//    /**
//     * deleteHistory does not work because the medical device has a state different than "NO_STATE".
//     */
//    @Test
//    void deleteHistoryErrorHasState() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.AVAILABLE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(StateException.class, () -> {
//            stateHandler.deleteHistory(new DeleteInput(medicalDevice, user));
//        });
//    }
//
//    /**
//     * deleteHistory does not work because the medical device does not exist in the state database.
//     */
//    @Test
//    void deleteHistoryErrorNotInOwnDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.deleteHistory(new DeleteInput(medicalDevice, user));
//        });
//    }
//
//    /**
//     * deleteHistory does not work because the medical device does not exist in the medical device database.
//     */
//    @Test
//    void deleteHistorySuccessNotInMdDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(false, "Does not exist"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.NO_STATE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        try {
//            stateHandler.deleteHistory(new DeleteInput(medicalDevice, user));
//        } catch (DeviceException | IncorrectAttributeException | UserException | StateException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).delete(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//    }
//
//    /**
//     * The state transition from AVAILABLE to IN_USE works with user level MEDICAL MEDIUM
//     * Device exists in state database and in medical device database
//     */
//    @Test
//    void changeStateSuccessAvailableInUse() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.MEDIUM));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.AVAILABLE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        try {
//            stateHandler.changeState(new StateInput(medicalDevice, user, StateNames.IN_USE));
//        } catch (DeviceException | IncorrectAttributeException | UserException | StateException | ConnectionException | UserLevelException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).saveAndFlush(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//        assertEquals(device.getCurrentState().getState(), StateNames.IN_USE);
//    }
//
//    /**
//     * The state transition from IN_USE to DEFECT_INSPECTION works with user level MEDICAL MEDIUM
//     * Device exists in state database and in medical device database
//     */
//    @Test
//    void changeStateSuccessInUseDefectInspection() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.MEDIUM));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        try {
//            stateHandler.changeState(new StateInput(medicalDevice, user, StateNames.DEFECT_INSPECTION));
//        } catch (DeviceException | IncorrectAttributeException | UserException | StateException | ConnectionException | UserLevelException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).saveAndFlush(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//        assertEquals(device.getCurrentState().getState(), StateNames.DEFECT_INSPECTION);
//    }
//
//    /**
//     * The state transition from DEFECT_INSPECTION to DEFECT_OWN_REPAIR does not work with user level MEDICAL HIGH
//     * Device exists in state database and in medical device database
//     */
//    @Test
//    void changeStateErrorDefectInspectionOwnRepair() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.DEFECT_INSPECTION, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(UserException.class, () -> {
//            stateHandler.changeState(new StateInput(medicalDevice, user, StateNames.DEFECT_OWN_REPAIR));
//        });
//    }
//
//    /**
//     * change state does not work because the medical device has state "NO_STATE". (user level is TECHNICAL HIGH)
//     */
//    @Test
//    void changeStateErrorNoState() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.NO_STATE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(UserException.class, () -> {
//            stateHandler.changeState(new StateInput(medicalDevice, user, StateNames.AVAILABLE));
//        });
//    }
//
//    /**
//     * change state does not work because the medical device is not present int the medical device database.
//     */
//    @Test
//    void changeStateErrorNotInMdDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(false, "Does not exist"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, null, new State(StateNames.AVAILABLE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.changeState(new StateInput(medicalDevice, user, StateNames.IN_USE));
//        });
//    }
//
//    /**
//     * change state does not work because the medical device is not present in the medical device database.
//     */
//    @Test
//    void changeStateErrorNotInOwnDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.changeState(new StateInput(medicalDevice, user, StateNames.IN_USE));
//        });
//    }
//
//    /**
//     * setPriority works with user level TECHNICAL HIGH
//     * Device exists in state database and in medical device database
//     */
//    @Test
//    void setPrioritySuccess() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        try {
//            stateHandler.setPriority(new DefectPriorityInput(medicalDevice, user, DefectPriority.MEDIUM));
//        } catch (DeviceException | IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).saveAndFlush(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//        assertEquals(device.getCurrentState().getState(), StateNames.IN_USE);
//        assertEquals(device.getDefectPriority(), DefectPriority.MEDIUM);
//    }
//
//    /**
//     * setPriority does not work because the user level is too low (MEDICAL LOW).
//     */
//    @Test
//    void setPriorityErrorUserLevel() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.LOW));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(UserException.class, () -> {
//            stateHandler.setPriority(new DefectPriorityInput(medicalDevice, user, DefectPriority.MEDIUM));
//        });
//    }
//
//    /**
//     * setPriority does not work because the medical device does not exist in medical device database
//     */
//    @Test
//    void setPriorityErrorNotInMdDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(false, "Does not exist"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.setPriority(new DefectPriorityInput(medicalDevice, user, DefectPriority.MEDIUM));
//        });
//    }
//
//    /**
//     * setPriority does not work because the medical device does not exist in state database
//     */
//    @Test
//    void setPriorityErrorNotInOwnDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.setPriority(new DefectPriorityInput(medicalDevice, user, DefectPriority.MEDIUM));
//        });
//    }
//
//    /**
//     * setAvailabilityDate works with user level TECHNICAL MEDIUM
//     * Device exists in state database and in medical device database
//     */
//    @Test
//    void setAvailabilityDateSuccess() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.MEDIUM));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.DEFECT_OWN_REPAIR, null, LocalDateTime.now()), new HistoryStatus())));
//
//        LocalDate tomorrow = LocalDate.now().plusDays(1);
//
//        try {
//            stateHandler.setAvailabilityDate(new AvailabilityDateInput(medicalDevice, user, tomorrow));
//        } catch (DeviceException | IncorrectAttributeException | UserException | StateException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).saveAndFlush(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//        assertEquals(device.getCurrentState().getState(), StateNames.DEFECT_OWN_REPAIR);
//        assertEquals(device.getCurrentState().getAvailabilityDate(), tomorrow);
//    }
//
//    /**
//     * setAvailabilityDate does not work because the user level is too low (MEDICAL LOW).
//     */
//    @Test
//    void setAvailabilityDateErrorUserLevel() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.LOW));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        LocalDate tomorrow = LocalDate.now().plusDays(1);
//
//        assertThrows(UserException.class, () -> {
//            stateHandler.setAvailabilityDate(new AvailabilityDateInput(medicalDevice, user, tomorrow));
//        });
//    }
//
//    /**
//     * setAvailabilityDate does not work because the medical device does not exist in medical device database
//     */
//    @Test
//    void setAvailabilityDateErrorNotInMdDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(false, "Does not exist"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        LocalDate tomorrow = LocalDate.now().plusDays(1);
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.setAvailabilityDate(new AvailabilityDateInput(medicalDevice, user, tomorrow));
//        });
//    }
//
//    /**
//     * setAvailabilityDate does not work because the medical device does not exist in state database
//     */
//    @Test
//    void setAvailabilityDateErrorNotInOwnDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//        LocalDate tomorrow = LocalDate.now().plusDays(1);
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.setAvailabilityDate(new AvailabilityDateInput(medicalDevice, user, tomorrow));
//        });
//    }
//
//    /**
//     * addAdditionalInformation works with user level MEDICAL LOW
//     * Device exists in state database and in medical device database
//     */
//    @Test
//    void addAdditionalInformationSuccess() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.LOW));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        try {
//            stateHandler.addAdditionalInformation(new AdditionalInformationInput(medicalDevice, user, "This is some information"));
//        } catch (DeviceException | IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        ArgumentCaptor<MedicalDevice> argument = ArgumentCaptor.forClass(MedicalDevice.class);
//        verify(medicalDeviceRepository).saveAndFlush(argument.capture());
//        MedicalDevice device = argument.getValue();
//
//        assertEquals(device.getDeviceId(), medicalDevice);
//        assertEquals(device.getCurrentState().getState(), StateNames.IN_USE);
//        assertEquals(device.getCurrentState().getAdditionalInformation().get(0).getInfo(), "This is some information");
//    }
//
//    /**
//     * addAdditionalInformation does not work because the message is too short
//     */
//    @Test
//    void addAdditionalInformationErrorMessageShort() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(IncorrectAttributeException.class, () -> {
//            stateHandler.addAdditionalInformation(new AdditionalInformationInput(medicalDevice, user, "123"));
//        });
//    }
//
//    /**
//     * addAdditionalInformation does not work because the user level is too low (MEDICAL INTERN).
//     */
//    @Test
//    void addAdditionalInformationErrorUserLevel() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.INTERN));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(UserException.class, () -> {
//            stateHandler.addAdditionalInformation(new AdditionalInformationInput(medicalDevice, user, "This is some information"));
//        });
//    }
//
//    /**
//     * addAdditionalInformation does not work because the medical device does not exist in the medical device database
//     */
//    @Test
//    void addAdditionalInformationErrorNotInMdDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.TECHNICAL, EmployeeRank.HIGH));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(false, "Does not exist"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), new HistoryStatus())));
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.addAdditionalInformation(new AdditionalInformationInput(medicalDevice, user, "This is some information"));
//        });
//    }
//
//    /**
//     * addAdditionalInformation does not work because the medical device does not exist in state database
//     */
//    @Test
//    void addAdditionalInformationErrorNotInOwnDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.INTERN));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.addAdditionalInformation(new AdditionalInformationInput(medicalDevice, user, "This is some information"));
//        });
//    }
//
//    /**
//     * getDeviceState works with user level MEDICAL INTERN
//     * Device exists in state database and in medical device database
//     */
//    @Test
//    void getDeviceStateSuccess() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        LocalDateTime time = LocalDateTime.now();
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.INTERN));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, user, time), new HistoryStatus())));
//
//        CurrentStateOutput output = null;
//
//        try {
//            output = stateHandler.getDeviceState(medicalDevice, user);
//        } catch (DeviceException | IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        assertEquals(medicalDevice, output.getDeviceId());
//        assertNull(output.getAvailabilityDate());
//        assertEquals(DefectPriority.LOW, output.getDefectPriority());
//        assertEquals(StateNames.IN_USE, output.getState());
//        assertEquals(time, output.getEnteredAt());
//        assertEquals(user, output.getEnteredBy());
//    }
//
//    /**
//     * getDeviceState works with user level MEDICAL INTERN
//     * Device exists in medical device database but not in state database
//     */
//    @Test
//    void getDeviceStateSuccessNotInOwnDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.INTERN));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.empty());
//
//        CurrentStateOutput output = null;
//
//        try {
//            output = stateHandler.getDeviceState(medicalDevice, user);
//        } catch (DeviceException | IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        assertEquals(medicalDevice, output.getDeviceId());
//        assertNull(output.getAvailabilityDate());
//        assertNull(output.getDefectPriority());
//        assertEquals(StateNames.NO_STATE, output.getState());
//        assertNull(output.getEnteredAt());
//        assertNull(output.getEnteredBy());
//    }
//
//    /**
//     * getDeviceState does not work because the user does not exist
//     */
//    @Test
//    void getDeviceStateErrorUserLevel() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        LocalDateTime time = LocalDateTime.now();
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(null);
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, user, time), new HistoryStatus())));
//
//        assertThrows(UserException.class, () -> {
//            stateHandler.getDeviceState(medicalDevice, user);
//        });
//    }
//
//    /**
//     * getDeviceState does not work because the medical device does not exist in the medical device database
//     */
//    @Test
//    void getDeviceStateErrorNotInMdDB() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        LocalDateTime time = LocalDateTime.now();
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.INTERN));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(false, "Does not exist"));
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, user, time), new HistoryStatus())));
//
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.getDeviceState(medicalDevice, user);
//        });
//    }
//
//    /**
//     * getDeviceHistory works with user level MEDICAL INTERN
//     * Device exists in state database and in medical device database
//     */
//    @Test
//    void getStateHistorySuccessAll() {
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.INTERN));
//        when(medicalDeviceClient.medicalDeviceExists(medicalDevice)).thenReturn(new BooleanResponse(true, "All fine"));
//        HistoryStatus historyStatus = new HistoryStatus();
//        when(medicalDeviceRepository.findByDeviceId(medicalDevice)).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.now()), historyStatus)));
//        List<State> states = new ArrayList<>();
//        states.add(new State(StateNames.BOUGHT, user, LocalDateTime.now()));
//        states.add(new State(StateNames.AVAILABLE, user, LocalDateTime.now()));
//        when(stateRepository.findAllByHistoryIdOrderByEnteredAtDesc(historyStatus.getId())).thenReturn(states);
//
//        HistoryOutput output = null;
//
//        try {
//            output = stateHandler.getStateHistory(new ShowHistoryInput(medicalDevice, user, null, null));
//        } catch (DeviceException | IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//
//        assertEquals(medicalDevice, output.getDeviceId());
//        assertEquals(2, output.getStates().size());
//        assertEquals(StateNames.BOUGHT, output.getStates().get(0).getState());
//        assertEquals(StateNames.AVAILABLE, output.getStates().get(1).getState());
//    }
//
//    @Test
//    void getStatesByDeviceIdsSuccess() {
//        UUID[] devices = {
//                UUID.fromString("77944141-15ae-4d4a-bd15-df0c3e1ef291"),
//                UUID.fromString("3e31e004-d87f-471f-a8c4-3cb5ee1527c7"),
//                UUID.fromString("d7cd097a-21d6-4e4e-ab4c-4f07a794d43c"),
//                UUID.fromString("8eb7e48a-0ce4-41a9-9c96-a01a2738c616"),
//                UUID.fromString("f793bef0-1fef-49de-b830-0a0b056fb864"),
//                UUID.fromString("b4e77c5d-90aa-4ece-9539-800ebbf1fe89"),
//                UUID.fromString("1c479a9f-506c-4bc0-9025-f364c531b168")
//        };
//
//        StateHandler stateHandler = new StateHandler(medicalDeviceRepository, stateRepository, medicalDeviceClient, permissionHandler);
//
//        when(staffClient.getUserLevelOld(user)).thenReturn(new UserLevelOld(EmployeeType.MEDICAL, EmployeeRank.INTERN));
//
//        when(medicalDeviceClient.medicalDeviceExists(devices[0])).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceClient.medicalDeviceExists(devices[1])).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceClient.medicalDeviceExists(devices[2])).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceClient.medicalDeviceExists(devices[3])).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceClient.medicalDeviceExists(devices[4])).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceClient.medicalDeviceExists(devices[5])).thenReturn(new BooleanResponse(true, "All fine"));
//        when(medicalDeviceClient.medicalDeviceExists(devices[6])).thenReturn(new BooleanResponse(true, "All fine"));
//
//        when(medicalDeviceRepository.findByDeviceId(devices[0])).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.IN_USE, null, LocalDateTime.of(2021, 1, 20, 14, 15)), new HistoryStatus())));
//        when(medicalDeviceRepository.findByDeviceId(devices[1])).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.LOW, new State(StateNames.AVAILABLE, null, LocalDateTime.of(2021, 2, 15, 18, 7)), new HistoryStatus())));
//        when(medicalDeviceRepository.findByDeviceId(devices[2])).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.MEDIUM, new State(StateNames.AVAILABLE, null, LocalDateTime.of(2021, 1, 11, 9, 40)), new HistoryStatus())));
//        when(medicalDeviceRepository.findByDeviceId(devices[3])).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.MEDIUM, new State(StateNames.DEFECT_INSPECTION, null, LocalDateTime.of(2021, 2, 11, 7, 33)), new HistoryStatus())));
//        when(medicalDeviceRepository.findByDeviceId(devices[4])).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.HIGH, new State(StateNames.DEFECT_OWN_REPAIR, null, LocalDateTime.of(2021, 1, 31, 20, 55)), new HistoryStatus())));
//        when(medicalDeviceRepository.findByDeviceId(devices[5])).thenReturn(Optional.empty());
//        when(medicalDeviceRepository.findByDeviceId(devices[6])).thenReturn(Optional.of(new MedicalDevice(medicalDevice, DefectPriority.HIGH, new State(StateNames.NO_STATE, null, LocalDateTime.of(2021, 1, 22, 4, 2)), new HistoryStatus())));
//
//        List<CurrentStateOutput> output = null;
//
//        try {
//            output = stateHandler.getFilteredDevicesByID(new FilteredDeviceListInput(null, null, null, null, devices, user));
//        } catch (IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(7, output.size());
//
//        try {
//            output = stateHandler.getFilteredDevicesByID(new FilteredDeviceListInput(List.of(StateNames.AVAILABLE), null, null, null, devices, user));
//        } catch (IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(2, output.size());
//
//        try {
//            output = stateHandler.getFilteredDevicesByID(new FilteredDeviceListInput(List.of(StateNames.NO_STATE), null, null, null, devices, user));
//        } catch (IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(2, output.size());
//
//        try {
//            output = stateHandler.getFilteredDevicesByID(new FilteredDeviceListInput(null, DefectPriority.MEDIUM, null, null, devices, user));
//        } catch (IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(2, output.size());
//
//        try {
//            output = stateHandler.getFilteredDevicesByID(new FilteredDeviceListInput(List.of(StateNames.AVAILABLE), DefectPriority.MEDIUM, null, null, devices, user));
//        } catch (IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(1, output.size());
//
//        try {
//            output = stateHandler.getFilteredDevicesByID(new FilteredDeviceListInput(null, null, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 3,1), devices, user));
//        } catch (IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(6, output.size());
//
//        try {
//            output = stateHandler.getFilteredDevicesByID(new FilteredDeviceListInput(null, null, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1,31), devices, user));
//        } catch (IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(4, output.size());
//
//        try {
//            output = stateHandler.getFilteredDevicesByID(new FilteredDeviceListInput(List.of(StateNames.AVAILABLE), null, LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1,31), devices, user));
//        } catch (IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(1, output.size());
//
//        try {
//            output = stateHandler.getFilteredDevicesByID(new FilteredDeviceListInput(List.of(StateNames.AVAILABLE, StateNames.IN_USE), null, null, null, devices, user));
//        } catch (IncorrectAttributeException | UserException | ConnectionException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(3, output.size());
//    }
}