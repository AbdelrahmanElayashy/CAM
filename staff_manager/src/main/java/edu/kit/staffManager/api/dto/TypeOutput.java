package edu.kit.staffManager.api.dto;

import edu.kit.staffManager.logic.model.userLevel.EmployeeRank;
import edu.kit.staffManager.logic.model.userLevel.EmployeeType;

import java.util.List;

public class TypeOutput {
    private String type;
    private List<String> ranks;

    public TypeOutput(String type, List<String> ranks) {
        this.type = type;
        this.ranks = ranks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getRanks() {
        return ranks;
    }

    public void setRanks(List<String> ranks) {
        this.ranks = ranks;
    }
}
