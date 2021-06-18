package edu.kit.stateManager.infrastructure.inputClasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.stateManager.logic.model.state.DefectPriority;
import edu.kit.stateManager.logic.model.state.StateNames;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class FilteredDeviceListInput {

    private List<StateNames> states;
    private DefectPriority defectPriority;
    private LocalDate from;
    private LocalDate to;
    private UUID[] devices;
    private UUID userId;

    public FilteredDeviceListInput(@JsonProperty("states") List<StateNames> states,
                                   @JsonProperty("priority") DefectPriority defectPriority,
                                   @JsonProperty("from") LocalDate from,
                                   @JsonProperty("to") LocalDate to) {
        this.states = states;
        this.defectPriority = defectPriority;
        this.from = from;
        this.to = to;
    }

    public FilteredDeviceListInput(List<StateNames> states, DefectPriority defectPriority, LocalDate from, LocalDate to, UUID[] devices, UUID userId) {
        this.states = states;
        this.defectPriority = defectPriority;
        this.from = from;
        this.to = to;
        this.devices = devices;
        this.userId = userId;
    }

    public FilteredDeviceListInput() {
        this.states = null;
        this.defectPriority = null;
        this.from = null;
        this.to = null;
    }

    public UUID[] getDevices() {
        return devices;
    }

    public void setDevices(UUID[] devices) {
        this.devices = devices;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<StateNames> getStates() {
        return states;
    }

    public void setStates(List<StateNames> states) {
        this.states = states;
    }

    public DefectPriority getDefectPriority() {
        return defectPriority;
    }

    public void setDefectPriority(DefectPriority defectPriority) {
        this.defectPriority = defectPriority;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public boolean hasRequiredInput() {
        return !(userId == null) && !(devices == null);
    }
}
