package edu.kit.staffManager.api.dto;

public class RankOutput {
    private String rank;
    private int importance;

    public RankOutput(String rank, int importance) {
        this.rank = rank;
        this.importance = importance;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
