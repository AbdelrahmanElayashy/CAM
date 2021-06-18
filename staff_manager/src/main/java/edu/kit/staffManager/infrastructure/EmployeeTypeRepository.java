package edu.kit.staffManager.infrastructure;

import edu.kit.staffManager.logic.model.userLevel.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, String> {
}
