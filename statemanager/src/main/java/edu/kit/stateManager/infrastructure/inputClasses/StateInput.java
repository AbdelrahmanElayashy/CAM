package edu.kit.stateManager.infrastructure.inputClasses;

import edu.kit.stateManager.logic.model.state.StateNames;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class StateInput {

    private UUID deviceId;
    private UUID userId;
    private StateNames state;

    public StateInput(@JsonProperty("deviceId") UUID deviceId,
                      @JsonProperty("userId") UUID userId,
                      @JsonProperty("state") StateNames state) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.state = state;
    }


    public UUID getDeviceId() {
        return deviceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public StateNames getState() {
        return state;
    }

    public boolean hasRequiredInputInitialize() {
        return !(deviceId == null) && !(userId == null);
    }

    public boolean hasRequiredInput() {
        return !(deviceId == null) && !(userId == null) && !(state == null);
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setState(StateNames state) {
        this.state = state;
    }
}
