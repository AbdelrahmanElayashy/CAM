package edu.kit.stateManager.infrastructure.dao;

import edu.kit.stateManager.logic.model.state.AdditionalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AdditionalInformationRepository extends JpaRepository<AdditionalInformation, Long> {

    AdditionalInformation findByInfo(String info);
}
