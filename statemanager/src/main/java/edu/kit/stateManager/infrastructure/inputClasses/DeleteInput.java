package edu.kit.stateManager.infrastructure.inputClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DeleteInput {

    private UUID deviceId;
    private UUID userId;

    public DeleteInput(@JsonProperty("deviceId") UUID deviceId,
                       @JsonProperty("userId") UUID userId) {
        this.deviceId = deviceId;
        this.userId = userId;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public boolean hasRequiredInput() {
        return !(deviceId == null) && !(userId == null);
    }
}
