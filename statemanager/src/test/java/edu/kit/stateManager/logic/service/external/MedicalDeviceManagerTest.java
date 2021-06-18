package edu.kit.stateManager.logic.service.external;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MedicalDeviceManagerTest {

    MedicalDeviceManager medicalDeviceManager = new MedicalDeviceManager();

    @Disabled
    @Test
    void medicalDeviceExists() {
        assertFalse(medicalDeviceManager.medicalDeviceExists(UUID.fromString("13af43f9-6f26-47eb-ba5f-0a080a866b29")).getValue());
        assertFalse(medicalDeviceManager.medicalDeviceExists(UUID.fromString("fb8337b9-3ea7-4602-a74a-230c10a2bd6d")).getValue());
        assertTrue(medicalDeviceManager.medicalDeviceExists(UUID.fromString("dc24b836-9753-4a32-a6f8-fa8d9a4e4b85")).getValue());
        assertTrue(medicalDeviceManager.medicalDeviceExists(UUID.fromString("bda01e72-d105-4c24-bfc2-101e5ad59be4")).getValue());
    }
}