package edu.kit.stateManager.logic.service;

import edu.kit.stateManager.infrastructure.dao.CommandPermissionRepository;
import edu.kit.stateManager.infrastructure.dao.StateTransitionPermissionRepository;
import edu.kit.stateManager.logic.model.state.StateNames;
import edu.kit.stateManager.logic.model.permission.Command;
import edu.kit.stateManager.logic.model.permission.CommandPermission;
import edu.kit.stateManager.logic.model.permission.StateTransitionPermission;
import edu.kit.stateManager.logic.model.permission.UserLevel;
import edu.kit.stateManager.logic.service.exceptions.*;
import edu.kit.stateManager.logic.service.external.StaffClient;
import edu.kit.stateManager.logic.service.external.StaffManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PermissionHandler {

    private final CommandPermissionRepository commandPermissionRepository;
    private final StateTransitionPermissionRepository stateTransitionPermissionRepository;
    private final StaffClient staffClient;

    @Autowired
    public PermissionHandler(CommandPermissionRepository commandPermissionRepository, StateTransitionPermissionRepository stateTransitionPermissionRepository, StaffManager staffManager) {
        this.commandPermissionRepository = commandPermissionRepository;
        this.stateTransitionPermissionRepository = stateTransitionPermissionRepository;
        this.staffClient = staffManager;
    }

    public void addStateTransitionPermission(StateNames from, StateNames to, UserLevel minUserLevel) throws ConnectionException, UserLevelException, StateException {
        if (from.equals(to)) throw new StateException("from and to can not be the same state");
        if (!staffClient.employeeTypeExists(minUserLevel.getEmployeeType())) {
            throw new UserLevelException("This employee type does not exist");
        }
        Optional<StateTransitionPermission> optPermission = stateTransitionPermissionRepository.findByEmployeeTypeAndFromStateAndToState(minUserLevel.getEmployeeType(), from, to);
        if (optPermission.isPresent()) return;  //TODO: Maybe call changePermission
        if (!staffClient.employeeRankExists(minUserLevel)) {
            throw new UserLevelException("This employee rank does not exist");
        }
        StateTransitionPermission permission = new StateTransitionPermission(from, to, minUserLevel);
        stateTransitionPermissionRepository.saveAndFlush(permission);
    }

    public void removeStateTransitionPermission(StateNames from, StateNames to, String employeeType) throws ConnectionException, UserLevelException {
        if (!staffClient.employeeTypeExists(employeeType)) {
            throw new UserLevelException("This employee type does not exist");
        }
        Optional<StateTransitionPermission> optPermission = stateTransitionPermissionRepository.findByEmployeeTypeAndFromStateAndToState(employeeType, from, to);
        if (optPermission.isEmpty()) return;
        StateTransitionPermission permission = optPermission.get();
        stateTransitionPermissionRepository.delete(permission);
    }

    public void changeStateTransitionPermissionMinUser(StateNames from, StateNames to, UserLevel minUserLevel) throws UserLevelException, ConnectionException, StateException {
        if (!staffClient.employeeTypeExists(minUserLevel.getEmployeeType())) {
            throw new UserLevelException("This employee type does not exist");
        }
        if (!staffClient.employeeRankExists(minUserLevel)) {
            throw new UserLevelException("This employee rank does not exist");
        }
        Optional<StateTransitionPermission> optPermission = stateTransitionPermissionRepository.findByEmployeeTypeAndFromStateAndToState(minUserLevel.getEmployeeType(), from, to);
        if (optPermission.isEmpty()) {
            throw new StateException("No state transition permission for this state transition and this employee type exists");
            //addStateTransitionPermission(from, to, minUserLevel);
            //return;
        }
        StateTransitionPermission permission = optPermission.get();
        permission.setEmployeeType(minUserLevel.getEmployeeType());
        permission.setMinimumEmployeeRank(minUserLevel.getEmployeeRank());
    }

    public BooleanResponse isValidStateTransition(StateNames from, StateNames to, UUID userId) throws ConnectionException, UserLevelException, UserException {
        UserLevel userLevel = staffClient.getUserLevel(userId);
        if (!staffClient.employeeTypeExists(userLevel.getEmployeeType())) {
            throw new UserLevelException("This employee type does not exist");
        }
        Optional<StateTransitionPermission> optPermission = stateTransitionPermissionRepository.findByEmployeeTypeAndFromStateAndToState(userLevel.getEmployeeType(), from, to);
        if (optPermission.isEmpty()) {
            return new BooleanResponse(false, "This state transition is not valid for a " + userLevel.getEmployeeType().toLowerCase() + " staff");
        }
        if (!staffClient.employeeRankExists(userLevel)) {
            throw new UserLevelException("This employee rank does not exist");
        }
        StateTransitionPermission permission = optPermission.get();
        if (staffClient.rankHigherEqualThan(permission.getMinUserLevel(), userLevel)) {
            return new BooleanResponse(true, "All fine");
        } else {
            return new BooleanResponse(false, "Your rank is not sufficient for this state transition");
        }
    }

    public void addCommandPermission(Command command, UserLevel minUserLevel) throws ConnectionException, UserLevelException {
        if (!staffClient.employeeTypeExists(minUserLevel.getEmployeeType())) {
            throw new UserLevelException("This employee type does not exist");
        }
        Optional<CommandPermission> optPermission = commandPermissionRepository.findByCommandAndType(command, minUserLevel.getEmployeeType());
        if (optPermission.isPresent()) return; //TODO: Maybe call changePermission
        if (!staffClient.employeeRankExists(minUserLevel)) {
            throw new UserLevelException("This employee rank does not exist");
        }
        CommandPermission commandPermission = new CommandPermission(command, minUserLevel);
        commandPermissionRepository.saveAndFlush(commandPermission);
    }

    public void removeCommandPermission(Command command, String employeeType) {

    }

    public void changeCommandPermissionMinUser(Command command, UserLevel minUserLevel) {

    }

    public BooleanResponse canPerformCommand(Command command, UserLevel userLevel) throws UserLevelException, ConnectionException {
        if (!staffClient.employeeTypeExists(userLevel.getEmployeeType())) {
            throw new UserLevelException("This employee type does not exist");
        }
        Optional<CommandPermission> optPermission = commandPermissionRepository.findByCommandAndType(command, userLevel.getEmployeeType());
        if (optPermission.isEmpty()) {
            return new BooleanResponse(false, "This command is not valid for a " + userLevel.getEmployeeType().toLowerCase() + " staff");
        }
        if (!staffClient.employeeRankExists(userLevel)) {
            throw new UserLevelException("This employee rank does not exist");
        }
        CommandPermission permission = optPermission.get();
        if (staffClient.rankHigherEqualThan(permission.getMinUserLevel(), userLevel)) {
            return new BooleanResponse(true, "All fine");
        } else {
            return new BooleanResponse(false, "Your rank is not sufficient for this state transition");
        }
    }

    public BooleanResponse canPerformCommand(Command command, UUID userId) throws UserException, ConnectionException {
        UserLevel userLevel = staffClient.getUserLevel(userId);
        try {
            return canPerformCommand(command, userLevel);
        } catch (UserLevelException e) {
            throw new UserException(e.getMessage());
        }
    }

    public List<StateNames> getPossibleNextStates(StateNames currentState, UUID userId) throws ConnectionException, UserException {
        UserLevel userLevel = staffClient.getUserLevel(userId);
        List<StateNames> states = new ArrayList<>();
        List<StateTransitionPermission> permissions = stateTransitionPermissionRepository.findAllByFromStateAndEmployeeType(currentState, userLevel.getEmployeeType());
        for (StateTransitionPermission permission: permissions) {
            if (staffClient.rankHigherEqualThan(permission.getMinUserLevel(), userLevel)) {
                states.add(permission.getToState());
            }
        }
        return states;
    }

    public boolean isValidEmployeeType(String type) throws ConnectionException {
        return staffClient.employeeTypeExists(type);
    }

    public boolean isValidEmployeeRank(String type, String rank) throws ConnectionException {
        return staffClient.employeeRankExists(new UserLevel(type, rank));
    }

    public void resetStatePermissionsForType(String type, List<StateTransitionPermission> permissions) {
        stateTransitionPermissionRepository.deleteAllByEmployeeType(type);
        stateTransitionPermissionRepository.saveAll(permissions);
        stateTransitionPermissionRepository.flush();
    }

    public void resetCommandPermissions(List<CommandPermission> permissions) {
        commandPermissionRepository.deleteAll();
        commandPermissionRepository.saveAll(permissions);
        commandPermissionRepository.flush();
    }

    public BooleanResponse isSuperUser(UUID userId) throws ConnectionException, UserException {
        UserLevel userLevel = staffClient.getUserLevel(userId);
        if (staffClient.rankHigherEqualThan(new UserLevel("SUPER_USER", "SUPER_USER"), userLevel)) {
            return new BooleanResponse(true, "All fine");
        } else {
            return new BooleanResponse(false, "Your rank is not sufficient for this state transition");
        }
    }
}
