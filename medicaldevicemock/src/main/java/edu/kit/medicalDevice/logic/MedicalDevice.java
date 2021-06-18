package edu.kit.medicalDevice.logic;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MedicalDevice {

    private List<Device> deviceList = new ArrayList<>();

    public MedicalDevice() {
        deviceList.add(new Device(UUID.fromString("739ea0f6-0a68-4a47-8f22-f262a05e9b07"), "device A"));
        deviceList.add(new Device(UUID.fromString("dc24b836-9753-4a32-a6f8-fa8d9a4e4b85"), "device B"));
        deviceList.add(new Device(UUID.fromString("be5dfafb-b9c5-4278-b6bc-14b6bea96229"), "device C"));
        deviceList.add(new Device(UUID.fromString("264cac7c-94b2-4e0f-8261-b544e8815f79"), "device D"));
        deviceList.add(new Device(UUID.fromString("bda01e72-d105-4c24-bfc2-101e5ad59be4"), "device E"));
        deviceList.add(new Device(UUID.fromString("12f9a194-b05d-4d96-b404-7162c28a6e96"), "device F"));
        deviceList.add(new Device(UUID.fromString("18dcbebd-25b5-4932-83f3-1345412ce15a"), "device G"));
        deviceList.add(new Device(UUID.fromString("fd6b4c5b-61dc-4b67-8e5d-dfe028885c98"), "device H"));
    }

    public boolean addMedicalDevice(UUID deviceId, String deviceName) {
        if (getDevice(deviceId) != null) {
            return false;
        } else {
            deviceList.add(new Device(deviceId, deviceName));
            return true;
        }
    }

    public Device getDevice(UUID deviceId) {
        for (Device device: deviceList) {
            if (device.getDeviceId().equals(deviceId)) {
                return device;
            }
        }
        return null;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }
}
