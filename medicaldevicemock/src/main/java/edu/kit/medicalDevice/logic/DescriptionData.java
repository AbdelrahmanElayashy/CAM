package edu.kit.medicalDevice.logic;

import java.time.LocalDate;

public class DescriptionData {
    private String manufacturer;
    private LocalDate manufacturerDate;
    private String serialNumber;
    private String deviceName;
    private String category;
    private String type;
    private String location;

    public DescriptionData(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public LocalDate getManufacturerDate() {
        return manufacturerDate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }
}
