package edu.kit.stateManager.logic.model.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;


@Getter
@AllArgsConstructor
public enum StateNames {
    BOUGHT("BOUGHT"), AVAILABLE("AVAILABLE"), RESERVED("RESERVED"), IN_USE("IN_USE"), CLEANING("CLEANING"),
    DEFECT_INSPECTION("DEFECT_INSPECTION"), MAINTENANCE("MAINTENANCE"), DEFECT_NO_REPAIR("DEFECT_NO_REPAIR"),
    DISPOSED("DISPOSED"), DEFECT_EXTERNAL_REPAIR("DEFECT_EXTERNAL_REPAIR"),
    DEFECT_OWN_REPAIR("DEFECT_OWN_REPAIR"), NO_STATE("NO_STATE");

    private String name;

    public boolean hasAvailabilityDate() {
        Set<StateNames> defectStates= new HashSet<>();
        defectStates.add(DEFECT_EXTERNAL_REPAIR);
        defectStates.add(DEFECT_OWN_REPAIR);
        defectStates.add(MAINTENANCE);
        return defectStates.contains(this);
    }

    public boolean isDefectState() {
        Set<StateNames> defectStates= new HashSet<>();
        defectStates.add(DEFECT_EXTERNAL_REPAIR);
        defectStates.add(DEFECT_OWN_REPAIR);
        defectStates.add(DEFECT_NO_REPAIR);
        defectStates.add(DISPOSED);
        defectStates.add(DEFECT_INSPECTION);
        return defectStates.contains(this);
    }
}
