package edu.kit.stateManager.logic.service.external;

import java.util.UUID;

public interface ReservationClient {
    public void deviceDefect(UUID deviceID);

    public boolean canChangeReservationStatus(UUID deviceId, UUID userId);
}
