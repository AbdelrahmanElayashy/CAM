package edu.kit.staffManager.logic;

import com.sun.jdi.InternalException;
import edu.kit.staffManager.api.dto.RankOutput;
import edu.kit.staffManager.api.dto.TypeOutput;
import edu.kit.staffManager.api.dto.UserDTO;
import edu.kit.staffManager.api.dto.UserLevel;
import edu.kit.staffManager.infrastructure.EmployeeRankRepository;
import edu.kit.staffManager.infrastructure.EmployeeTypeRepository;
import edu.kit.staffManager.infrastructure.UserRepository;
import edu.kit.staffManager.logic.exceptions.UserException;
import edu.kit.staffManager.logic.exceptions.UserLevelException;
import edu.kit.staffManager.logic.model.User;
import edu.kit.staffManager.logic.model.userLevel.EmployeeRank;
import edu.kit.staffManager.logic.model.userLevel.EmployeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffManager {

    private final EmployeeTypeRepository typeRepository;
    private final EmployeeRankRepository rankRepository;
    private final UserRepository userRepository;

    @Autowired
    public StaffManager(EmployeeTypeRepository typeRepository, EmployeeRankRepository rankRepository, UserRepository userRepository) {
        this.typeRepository = typeRepository;
        this.rankRepository = rankRepository;
        this.userRepository = userRepository;
    }

    @Deprecated
    public List<TypeOutput> getUserLevels() {
        List<TypeOutput> output = new ArrayList<>();
        List<EmployeeType> types = typeRepository.findAll();
        for (EmployeeType type: types) {
            List<EmployeeRank> ranks = rankRepository.findAllByTypeOrderByImportance(type);
            List<String> rankNames = new ArrayList<>();
            for (EmployeeRank rank: ranks) {
                rankNames.add(rank.getRank() + ", Importance: " + rank.getImportance());
            }
            TypeOutput out = new TypeOutput(type.getType(), rankNames);
            output.add(out);
        }
        return output;
    }

    public List<String> getTypes() {
        List<String> output = new ArrayList<>();
        List<EmployeeType> types = typeRepository.findAll();
        for (EmployeeType type: types) {
            output.add(type.getType());
        }
        return output;
    }

    public List<RankOutput> getRanksOfType(String typeName) {
        List<RankOutput> output = new ArrayList<>();
        Optional<EmployeeType> optType = typeRepository.findById(typeName);
        if (optType.isEmpty()) {
            throw new IllegalArgumentException("This type does not exist");
        }
        EmployeeType type = optType.get();
        List<EmployeeRank> ranks = rankRepository.findAllByTypeOrderByImportance(type);
        for (EmployeeRank rank: ranks) {
            output.add(new RankOutput(rank.getRank(), rank.getImportance()));
        }
        return output;
    }

    public UUID login(String username, String password) {
        Optional<User> user = userRepository.findByPasswordAndUserName(password, username);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("This combination of username and password does not exist");
        }
        return user.get().getUserId();
    }

    public void addType(String name) {
        Optional<EmployeeType> optType = typeRepository.findById(name);
        if (optType.isPresent()) {
            throw new IllegalArgumentException("This type already exists");
        }
        EmployeeType type = new EmployeeType();
        type.setType(name);
        typeRepository.saveAndFlush(type);
    }

    public void addRank(String typeName, String rankName) {
        Optional<EmployeeType> optType = typeRepository.findById(typeName);
        if (optType.isEmpty()) {
            throw new IllegalArgumentException("This type does not exist");
        }
        EmployeeType type = optType.get();
        Optional<EmployeeRank> rankOptional = rankRepository.findByTypeAndRank(type, rankName);
        if (rankOptional.isPresent()) {
            throw new IllegalArgumentException("This rank does already exist");
        }
        List<EmployeeRank> ranks = rankRepository.findAllByTypeOrderByImportance(type);
        int importance;
        if (ranks.isEmpty()) {
            importance = 0;
        } else {
            importance = ranks.get(ranks.size() - 1).getImportance() + 1;
        }
        EmployeeRank rank = new EmployeeRank();
        rank.setRank(rankName);
        rank.setType(type);
        rank.setImportance(importance);
        rankRepository.saveAndFlush(rank);
    }

    public int changeImportance(String typeName, String rankName, int importance) {
        Optional<EmployeeType> optType = typeRepository.findById(typeName);
        if (optType.isPresent()) {
            EmployeeType type = optType.get();
            Optional<EmployeeRank> optExists = rankRepository.findAllByTypeAndImportance(type, importance);
            if (optExists.isPresent()) {
                if (optExists.get().getRank().equals(rankName)) {
                    return importance;
                }
                throw new IllegalArgumentException("This importance for type " + typeName + " does already exist");
            }
            Optional<EmployeeRank> optRank = rankRepository.findByTypeAndRank(type, rankName);
            if (optRank.isPresent()) {
                EmployeeRank rank = optRank.get();
                rank.setImportance(importance);
                rankRepository.saveAndFlush(rank);
            } else throw new IllegalArgumentException("This rank does not exist");
        } else throw new IllegalArgumentException("This type does not exist");
        return importance;
    }

    public void swapImportance(String typeName, String rankName1, String rankName2) {
        Optional<EmployeeType> optType = typeRepository.findById(typeName);
        if (optType.isEmpty()) {
            throw new IllegalArgumentException("This type does not exist");
        }
        EmployeeType type = optType.get();
        Optional<EmployeeRank> optRank1 = rankRepository.findByTypeAndRank(type, rankName1);
        Optional<EmployeeRank> optRank2 = rankRepository.findByTypeAndRank(type, rankName2);
        if (optRank1.isEmpty() || optRank2.isEmpty()) {
            throw new IllegalArgumentException("One of the ranks does not exist");
        }
        EmployeeRank rank1 = optRank1.get();
        EmployeeRank rank2 = optRank2.get();
        int importance1 = rank1.getImportance();
        int importance2 = rank2.getImportance();
        rank1.setImportance(importance2);
        rank2.setImportance(importance1);
        rankRepository.saveAndFlush(rank1);
        rankRepository.saveAndFlush(rank2);
    }

    public void insertImportance(String typeName, String rankName, int importance) {
        Optional<EmployeeType> optType = typeRepository.findById(typeName);
        if (optType.isEmpty()) {
            throw new IllegalArgumentException("This type does not exist");
        }
        EmployeeType type = optType.get();
        Optional<EmployeeRank> optRank = rankRepository.findByTypeAndRank(type, rankName);
        if (optRank.isEmpty()) {
            throw new IllegalArgumentException("This rank does not exist");
        }
        EmployeeRank rank = optRank.get();
        List<EmployeeRank> ranks = rankRepository.findAllByTypeOrderByImportance(type);
        for (EmployeeRank toChange: ranks) {
            int oldImportance = toChange.getImportance();
            if (oldImportance >= importance) {
                toChange.setImportance(oldImportance + 1);
                rankRepository.saveAndFlush(toChange);
            }
        }
        rank.setImportance(importance);
        rankRepository.saveAndFlush(rank);
    }

    public boolean employeeTypeExists(String employeeType) {
        Optional<EmployeeType> optType = typeRepository.findById(employeeType);
        return optType.isPresent();
    }

    public boolean employeeRankExists(String employeeType, String employeeRank) {
        Optional<EmployeeType> optType = typeRepository.findById(employeeType);
        if (optType.isEmpty()) return false;
        EmployeeType type = optType.get();
        Optional<EmployeeRank> optRank = rankRepository.findByTypeAndRank(type, employeeRank);
        return optRank.isPresent();
    }

    public void addUser(UserDTO userDTO) {
        Optional<EmployeeType> optType = typeRepository.findById(userDTO.getEmployeeType());
        if (optType.isEmpty()) {
            throw new IllegalArgumentException("This type does not exist");
        }
        EmployeeType type = optType.get();
        Optional<EmployeeRank> optRank = rankRepository.findByTypeAndRank(type, userDTO.getEmployeeRank());
        if (optRank.isEmpty()) {
            throw new IllegalArgumentException("This rank does not exist");
        }
        EmployeeRank rank = optRank.get();
        UUID userId;
        if (userDTO.getUserId() == null) {
            userId = UUID.randomUUID();
            while (userRepository.findById(userId).isPresent()) {
                userId = UUID.randomUUID();
            }
        } else {
            userId = userDTO.getUserId();
            if (userRepository.findById(userId).isPresent()) {
                throw new IllegalArgumentException("This userId already exists");
            }
        }
        User user = new User(userId, userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getUserName(), userDTO.getPassword(), rank);
        userRepository.saveAndFlush(user);
    }

    public UserLevel getUserLevel(UUID id) throws UserException {
        Optional<User> optUser= userRepository.findById(id);
        if (optUser.isEmpty()) throw new UserException("User does not exist");
        User user = optUser.get();
        if (user.getRank().getRank() == null) throw new UserException("User does not have a user level");
        return new UserLevel(user.getRank().getType().getType(), user.getRank().getRank());
    }

    public void addSuperUser() {
        Optional<EmployeeType> optSuperType = typeRepository.findById("SUPER_USER");
        if (optSuperType.isEmpty()) {
            EmployeeType type = new EmployeeType();
            type.setType("SUPER_USER");
            typeRepository.saveAndFlush(type);
            optSuperType = typeRepository.findById("SUPER_USER");
        }
        if (optSuperType.isEmpty()) {
            throw new InternalException("cannot create superuser");
        }
        EmployeeType superType = optSuperType.get();
        Optional<EmployeeRank> optSuperRank = rankRepository.findByTypeAndRank(superType, "SUPER_USER");
        if (optSuperRank.isEmpty()) {
            addRank(superType.getType(), "SUPER_USER");
            optSuperRank = rankRepository.findByTypeAndRank(superType, "SUPER_USER");
        }
        if (optSuperRank.isEmpty()) {
            throw new InternalException("cannot create superuser");
        }
        EmployeeRank superRank = optSuperRank.get();
        List<User> superUsers = userRepository.findAllByRank(superRank);
        if (superUsers.isEmpty()) {
            UserDTO superUser = new UserDTO("superuser", "password", "SUPER_USER", "SUPER_USER");
            superUser.setUserId(UUID.fromString("fa527b41-7fea-4718-a48f-c0899f4990a5"));
            addUser(superUser);
        }
    }

    public boolean userLevelHigherEquals(String minEmployeeType, String minEmployeeRank, String actualEmployeeType, String actualEmployeeRank) throws UserLevelException {
        if (!minEmployeeType.equals(actualEmployeeType)) {
            throw new UserLevelException("User levels don not have the same type");
        }
        Optional<EmployeeType> optType = typeRepository.findById(minEmployeeType);
        if (optType.isEmpty()) {
            throw new UserLevelException("Employee type does not exist");
        }
        EmployeeType type = optType.get();
        Optional<EmployeeRank> optMinRank = rankRepository.findByTypeAndRank(type, minEmployeeRank);
        Optional<EmployeeRank> optActualRank = rankRepository.findByTypeAndRank(type, actualEmployeeRank);
        if (optMinRank.isEmpty() || optActualRank.isEmpty()) {
            throw new UserLevelException("Not all employee ranks exist");
        }
        EmployeeRank minRank = optMinRank.get();
        EmployeeRank actualRank = optActualRank.get();
        return actualRank.getImportance() <= minRank.getImportance();
    }
}
