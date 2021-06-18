package edu.kit.stateManager.logic.service.external;

import edu.kit.stateManager.logic.model.permission.UserLevel;
import edu.kit.stateManager.logic.service.exceptions.ConnectionException;
import edu.kit.stateManager.logic.service.exceptions.UserException;

import java.util.UUID;

public interface StaffClient {

    UserLevel getUserLevel(UUID userId) throws ConnectionException, UserException;

    boolean employeeTypeExists(String employeeType) throws ConnectionException;

    boolean employeeRankExists(UserLevel userLevel) throws ConnectionException;

    boolean rankHigherEqualThan(UserLevel minUserLevel, UserLevel actualUserLevel) throws ConnectionException;
}
