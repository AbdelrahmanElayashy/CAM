package edu.kit.stateManager.infrastructure.dao;

import edu.kit.stateManager.logic.model.permission.Command;
import edu.kit.stateManager.logic.model.permission.CommandPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface CommandPermissionRepository extends JpaRepository<CommandPermission, Long> {

    Optional<CommandPermission> findByCommandAndType(Command command, String employeeType);
}
