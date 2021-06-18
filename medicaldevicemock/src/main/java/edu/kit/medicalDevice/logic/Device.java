package edu.kit.medicalDevice.logic;

import java.util.UUID;

public class Device {
    private UUID deviceId;
    private String state;
    private DescriptionData descriptionData;

    public Device(UUID deviceId, String name) {
        this.deviceId = deviceId;
        this.descriptionData = new DescriptionData(name);
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public DescriptionData getDescriptionData() {
        return descriptionData;
    }

    public String getState() {
        return state;
    }
}
