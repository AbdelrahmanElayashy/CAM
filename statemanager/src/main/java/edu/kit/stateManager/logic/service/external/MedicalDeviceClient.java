package edu.kit.stateManager.logic.service.external;

import edu.kit.stateManager.logic.service.exceptions.BooleanResponse;

import java.util.UUID;

public interface MedicalDeviceClient {

    /**
     * Send a request to the medical device microservice to check, if the medical device exists in the medical device
     * database.
     * @param deviceId Id of the medical device
     * @return true if it exists, false with error message instead
     */
    BooleanResponse medicalDeviceExists(UUID deviceId);

}
