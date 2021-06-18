package edu.kit.stateManager.logic.service.external;

import edu.kit.stateManager.infrastructure.dao.MedicalDeviceRepository;
import edu.kit.stateManager.logic.model.state.MedicalDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationManager implements ReservationClient {

    private final MedicalDeviceRepository medicalDeviceRepository;

    @Autowired
    public ReservationManager(MedicalDeviceRepository medicalDeviceRepository) {
        this.medicalDeviceRepository = medicalDeviceRepository;
    }

    @Override
    public void deviceDefect(UUID deviceID) {

    }

    @Override
    public boolean canChangeReservationStatus(UUID deviceId, UUID userId) {
        Optional<MedicalDevice> opt = medicalDeviceRepository.findByDeviceId(deviceId);
        if (opt.isEmpty()) {
            return false;
        }
        MedicalDevice medicalDevice = opt.get();
        return medicalDevice.getCurrentState().getEnteredBy().equals(userId);
    }
}
