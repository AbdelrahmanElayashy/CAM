package edu.kit.stateManager.infrastructure.outputClasses;

import edu.kit.stateManager.logic.model.state.DefectPriority;
import edu.kit.stateManager.logic.model.state.StateNames;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CurrentStateOutput {

    private UUID deviceId;
    private DefectPriority defectPriority;
    protected StateNames state;
    protected UUID enteredBy;
    protected LocalDateTime enteredAt;
    protected LocalDate availabilityDate;
    protected List<AdditionalInformationOutput> additionalInformation;

    public CurrentStateOutput(UUID deviceId, DefectPriority defectPriority, StateNames state, UUID enteredBy, LocalDateTime enteredAt, LocalDate availabilityDate, List<AdditionalInformationOutput> additionalInformation) {
        this.deviceId = deviceId;
        this.defectPriority = defectPriority;
        this.state = state;
        this.enteredBy = enteredBy;
        this.enteredAt = enteredAt;
        this.availabilityDate = availabilityDate;
        this.additionalInformation = additionalInformation;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public DefectPriority getDefectPriority() {
        return defectPriority;
    }

    public StateNames getState() {
        return state;
    }

    public UUID getEnteredBy() {
        return enteredBy;
    }

    public LocalDateTime getEnteredAt() {
        return enteredAt;
    }

    public LocalDate getAvailabilityDate() {
        return availabilityDate;
    }

    public List<AdditionalInformationOutput> getAdditionalInformation() {
        return additionalInformation;
    }
}
