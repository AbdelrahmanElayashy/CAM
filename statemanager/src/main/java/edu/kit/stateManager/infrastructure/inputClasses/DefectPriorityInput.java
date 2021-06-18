package edu.kit.stateManager.infrastructure.inputClasses;

import edu.kit.stateManager.logic.model.state.DefectPriority;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DefectPriorityInput {

    private UUID deviceId;
    private UUID userId;
    private DefectPriority defectPriority;

    public DefectPriorityInput(@JsonProperty("deviceId") UUID deviceId,
                               @JsonProperty("userId") UUID userId,
                               @JsonProperty("priority") DefectPriority defectPriority) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.defectPriority = defectPriority;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public UUID getUserId() {
        return userId;
    }

    public DefectPriority getDefectPriority() {
        return defectPriority;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setDefectPriority(DefectPriority defectPriority) {
        this.defectPriority = defectPriority;
    }

    public boolean hasRequiredInput() {
        return !(deviceId == null) && !(userId == null) && !(defectPriority == null);
    }
}
