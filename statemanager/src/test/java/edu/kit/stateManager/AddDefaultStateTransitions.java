package edu.kit.stateManager;

import edu.kit.stateManager.logic.model.state.StateNames;
import edu.kit.stateManager.logic.model.permission.Command;
import edu.kit.stateManager.logic.model.permission.UserLevel;
import edu.kit.stateManager.logic.service.PermissionHandler;
import edu.kit.stateManager.logic.service.exceptions.ConnectionException;
import edu.kit.stateManager.logic.service.exceptions.StateException;
import edu.kit.stateManager.logic.service.exceptions.UserLevelException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest
class AddDefaultStateTransitions {

    UserLevel MeNo = new UserLevel("MEDICAL", "NORMAL");
    UserLevel TeNo = new UserLevel("TECHNICAL", "NORMAL");
    UserLevel TeAd = new UserLevel("TECHNICAL", "ADMIN");

    private final PermissionHandler permissionHandler;

    @Autowired
    public AddDefaultStateTransitions(PermissionHandler permissionHandler) {
        this.permissionHandler = permissionHandler;
    }

    StateNames[] stateNames = {
            StateNames.BOUGHT,
            StateNames.AVAILABLE,
            StateNames.RESERVED,
            StateNames.IN_USE,
            StateNames.CLEANING,
            StateNames.DEFECT_INSPECTION,
            StateNames.MAINTENANCE,
            StateNames.DEFECT_NO_REPAIR,
            StateNames.DISPOSED,
            StateNames.DEFECT_EXTERNAL_REPAIR,
            StateNames.DEFECT_OWN_REPAIR,
            StateNames.NO_STATE
    };

    private final UserLevel[][] medicalStaffTransitions = {
        //TO: B    | A     | R     | I_U   | C     | D_I   | M     | D_N_R | Disp  | D_E_R | D_O_R | N_S
            {null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null},     //FROM BOUGHT
            {null,   null,   MeNo,   MeNo,   null,   MeNo,   null,   null,   null,   null,   null,   null},     //FROM AVAILABLE
            {null,   MeNo,   null,   MeNo,   null,   MeNo,   null,   null,   null,   null,   null,   null},     //FROM RESERVED     TODO: from reserved to available only for person who reserved
            {null,   null,   null,   null,   MeNo,   MeNo,   null,   null,   null,   null,   null,   null},     //FROM IN_USE
            {null,   MeNo,   null,   null,   null,   MeNo,   null,   null,   null,   null,   null,   null},     //FROM CLEANING
            {null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null},     //FROM DEFECT_INSPECTION
            {null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null},     //MAINTENANCE
            {null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null},     //FROM DEFECT_NO_REPAIR
            {null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null},     //FROM DISPOSED
            {null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null},     //FROM DEFECT_EXTERNAL_REPAIR
            {null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null},     //FROM DEFECT_OWN_REPAIR
            {null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null}      //FROM NO_STATE

    };

    private final UserLevel[][] technicalStaffTransitions = {
        //TO: B    | A     | R     | I_U   | C     | D_I   | M     | D_N_R | Disp  | D_E_R | D_O_R | N_S
            {null,   TeNo,   TeAd,   TeAd,   TeAd,   TeNo,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   null},     //FROM BOUGHT
            {TeAd,   null,   TeNo,   TeNo,   TeAd,   TeNo,   TeNo,   TeAd,   TeAd,   TeAd,   TeAd,   null},     //FROM AVAILABLE
            {TeAd,   TeAd,   null,   TeNo,   TeAd,   TeNo,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   null},     //FROM RESERVED     TODO: from reserved to available only for person who reserved
            {TeAd,   TeAd,   TeAd,   null,   TeNo,   TeNo,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   null},     //FROM IN_USE
            {TeAd,   TeNo,   TeAd,   TeAd,   null,   TeNo,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   null},     //FROM CLEANING
            {TeAd,   TeAd,   TeAd,   TeAd,   TeNo,   null,   TeAd,   TeNo,   TeAd,   TeNo,   TeNo,   null},     //FROM DEFECT_INSPECTION
            {TeAd,   TeAd,   TeAd,   TeAd,   TeNo,   TeAd,   null,   TeNo,   TeAd,   TeNo,   TeNo,   null},     //MAINTENANCE
            {TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   null,   TeNo,   TeAd,   TeAd,   null},     //FROM DEFECT_NO_REPAIR
            {TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   null,   TeAd,   TeAd,   null},     //FROM DISPOSED
            {TeAd,   TeAd,   TeAd,   TeAd,   TeNo,   TeAd,   TeAd,   TeAd,   TeAd,   null,   TeAd,   null},     //FROM DEFECT_EXTERNAL_REPAIR
            {TeAd,   TeAd,   TeAd,   TeAd,   TeNo,   TeAd,   TeAd,   TeAd,   TeAd,   TeAd,   null,   null},     //FROM DEFECT_OWN_REPAIR
            {null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null,   null}      //FROM NO_STATE

    };

    @Test
    void addDefaultStateTransitions() {
        insertPermissions(medicalStaffTransitions);
        insertPermissions(technicalStaffTransitions);
    }

    private void insertPermissions(UserLevel[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int b = 0; b < array[i].length; b++) {
                if (array[i][b] != null) {
                    try {
                        permissionHandler.addStateTransitionPermission(stateNames[i], stateNames[b], array[i][b]);
                    } catch (ConnectionException | UserLevelException | StateException e) {
                        fail();
                    }
                }
            }
        }
    }

    @Test
    void addDefaultCommandPermissions() throws ConnectionException, UserLevelException {
        UserLevel technicalNormal = new UserLevel("TECHNICAL", "NORMAL");
        UserLevel technicalAdmin = new UserLevel("TECHNICAL", "ADMIN");
        UserLevel medicalNormal = new UserLevel("MEDICAL", "NORMAL");

        permissionHandler.addCommandPermission(Command.GETTER, technicalNormal);
        permissionHandler.addCommandPermission(Command.GETTER, medicalNormal);

        permissionHandler.addCommandPermission(Command.SET_DEFECT_PRIORITY, technicalNormal);

        permissionHandler.addCommandPermission(Command.ADD_ADDITIONAL_INFORMATION, technicalNormal);
        permissionHandler.addCommandPermission(Command.ADD_ADDITIONAL_INFORMATION, medicalNormal);

        permissionHandler.addCommandPermission(Command.REMOVE_STATE, technicalAdmin);

        permissionHandler.addCommandPermission(Command.ADD_INITIAL_STATE, technicalAdmin);

        permissionHandler.addCommandPermission(Command.SET_AVAILABILITY_DATE, technicalNormal);

        permissionHandler.addCommandPermission(Command.DELETE_HISTORY, technicalAdmin);

        permissionHandler.addCommandPermission(Command.RESERVE_DEVICE, technicalNormal);
        permissionHandler.addCommandPermission(Command.RESERVE_DEVICE, medicalNormal);
    }
}