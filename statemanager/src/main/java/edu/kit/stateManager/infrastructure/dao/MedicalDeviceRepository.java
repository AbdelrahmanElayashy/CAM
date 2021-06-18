package edu.kit.stateManager.infrastructure.dao;

import edu.kit.stateManager.logic.model.state.MedicalDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;


@RepositoryRestResource
public interface MedicalDeviceRepository  extends JpaRepository<MedicalDevice, Long> {

     Optional<MedicalDevice> findByDeviceId(@Param("deviceId") UUID deviceId);


}

