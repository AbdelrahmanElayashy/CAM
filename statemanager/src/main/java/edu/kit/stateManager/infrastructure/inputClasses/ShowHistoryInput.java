package edu.kit.stateManager.infrastructure.inputClasses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public class ShowHistoryInput {

    private UUID deviceId;
    private UUID userId;
    private LocalDate from;
    private LocalDate to;

    public ShowHistoryInput(@JsonProperty("deviceId") UUID deviceId,
                            @JsonProperty("userId") UUID userId,
                            @JsonProperty("dateFrom") LocalDate from,
                            @JsonProperty("dateTo") LocalDate to) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.from = from;
        this.to = to;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
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
