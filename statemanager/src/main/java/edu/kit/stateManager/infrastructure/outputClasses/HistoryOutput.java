package edu.kit.stateManager.infrastructure.outputClasses;

import java.util.List;
import java.util.UUID;

public class HistoryOutput {
    private UUID deviceId;
    private List<StateOutput> states;

    public HistoryOutput(UUID deviceId, List<StateOutput> states) {
        this.deviceId = deviceId;
        this.states = states;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public List<StateOutput> getStates() {
        return states;
    }
}
