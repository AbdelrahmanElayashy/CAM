package edu.kit.stateManager.logic.model.state;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefectPriority {

    LOW("LOW"), MEDIUM("MEDIUM"), HIGH("HIGH");

    private String name;
}
