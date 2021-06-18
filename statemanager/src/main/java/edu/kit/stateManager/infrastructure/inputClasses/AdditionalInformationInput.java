package edu.kit.stateManager.infrastructure.inputClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AdditionalInformationInput {

    private UUID deviceId;
    private UUID userId;
    private String information;

    public AdditionalInformationInput(@JsonProperty("deviceId") UUID deviceId,
                                      @JsonProperty("userId") UUID userId,
                                      @JsonProperty("information") String information) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.information = information;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getInformation() {
        return information;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public boolean hasRequiredInput() {
        return !(deviceId == null) && !(userId == null) && !(information == null);
    }
}
