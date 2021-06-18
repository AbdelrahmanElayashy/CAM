package edu.kit.stateManager.infrastructure.dao;

import edu.kit.stateManager.logic.model.state.StateNames;
import edu.kit.stateManager.logic.model.permission.StateTransitionPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface StateTransitionPermissionRepository extends JpaRepository<StateTransitionPermission, Long> {

    Optional<StateTransitionPermission> findByEmployeeTypeAndFromStateAndToState(String employeeType, StateNames fromState, StateNames toState);

    List<StateTransitionPermission> findAllByFromStateAndEmployeeType(StateNames from, String employeeType);

    void deleteAllByEmployeeType(String employeeType);
}
