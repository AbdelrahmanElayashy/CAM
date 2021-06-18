package edu.kit.stateManager.infrastructure.outputClasses;

import java.time.LocalDateTime;
import java.util.UUID;

public class AdditionalInformationOutput {

    private UUID user;
    private LocalDateTime time;
    private String information;

    public AdditionalInformationOutput(UUID user, LocalDateTime time, String information) {
        this.user = user;
        this.time = time;
        this.information = information;
    }

    public UUID getUser() {
        return user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getInformation() {
        return information;
    }
}
