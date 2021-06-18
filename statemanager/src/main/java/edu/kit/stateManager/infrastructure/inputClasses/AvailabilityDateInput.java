package edu.kit.stateManager.infrastructure.inputClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public class AvailabilityDateInput {

    private UUID deviceId;
    private UUID userId;
    private LocalDate availabilityDate;

    public AvailabilityDateInput(@JsonProperty("deviceId") UUID deviceId,
                                 @JsonProperty("userId") UUID userId,
                                 @JsonProperty("availabilityDate") LocalDate availabilityDate) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.availabilityDate = availabilityDate;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDate getAvailabilityDate() {
        return availabilityDate;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setAvailabilityDate(LocalDate availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public boolean hasRequiredInput() {
        return !(deviceId == null) && !(userId == null) && !(availabilityDate == null);
    }
}
