package edu.kit.stateManager.logic.model.permission;

public enum Command {
    /**
     * getDeviceStatesByIds, getState, showHistory
     */
    GETTER,

    SET_DEFECT_PRIORITY,

    ADD_ADDITIONAL_INFORMATION,

    ADD_INITIAL_STATE,

    REMOVE_STATE,

    SET_AVAILABILITY_DATE,

    DELETE_HISTORY,

    RESERVE_DEVICE
}
