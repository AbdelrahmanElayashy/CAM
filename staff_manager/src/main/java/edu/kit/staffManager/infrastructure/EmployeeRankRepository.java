package edu.kit.staffManager.infrastructure;

import edu.kit.staffManager.logic.model.userLevel.EmployeeRank;
import edu.kit.staffManager.logic.model.userLevel.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface EmployeeRankRepository extends JpaRepository<EmployeeRank, String> {

    List<EmployeeRank> findAllByTypeOrderByImportance(EmployeeType type);

    Optional<EmployeeRank> findByTypeAndRank(EmployeeType type, String rank);

    Optional<EmployeeRank> findAllByTypeAndImportance(EmployeeType type, int importance);

    List<EmployeeRank> findAllByTypeAndImportanceAfter(EmployeeType type, int importance);
}
