package edu.kit.stateManager.infrastructure.dao;

import edu.kit.stateManager.logic.model.state.HistoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HistoryStatusRepository extends JpaRepository<HistoryStatus, Long> {
}
