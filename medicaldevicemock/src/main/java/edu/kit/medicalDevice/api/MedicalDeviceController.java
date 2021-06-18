package edu.kit.medicalDevice.api;

import edu.kit.medicalDevice.logic.Device;
import edu.kit.medicalDevice.logic.MedicalDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RequestMapping("medicalDevice")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MedicalDeviceController {

    private final MedicalDevice medicalDevice;

    @Autowired
    MedicalDeviceController(MedicalDevice medicalDevice) {
        this.medicalDevice = medicalDevice;
    }

    @PostMapping(path = "medical-device/addDevice")
    public void addDevice(@RequestParam("deviceId") UUID deviceId, @RequestParam("name") String name) {
        if (!medicalDevice.addMedicalDevice(deviceId, name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Device already exists");
        }
    }

    @GetMapping(path = "medical-devices")
    public List<Device> getDevices() {
        return medicalDevice.getDeviceList();
    }

    @GetMapping(path = "medical-device/{id}")
    public Device getDevice(@PathVariable("id") UUID deviceId) {
        Device device = medicalDevice.getDevice(deviceId);
        if (device == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Device does not exist");
        } else {
            return device;
        }
    }
}
