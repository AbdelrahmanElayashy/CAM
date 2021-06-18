package edu.kit.staffManager.infrastructure;

import edu.kit.staffManager.logic.model.User;
import edu.kit.staffManager.logic.model.userLevel.EmployeeRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByRank(EmployeeRank rank);

    Optional<User> findByPasswordAndUserName(String password, String userName);
}
