package edu.kit.staffManager.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kit.staffManager.api.dto.RankOutput;
import edu.kit.staffManager.api.dto.TypeOutput;
import edu.kit.staffManager.api.dto.UserDTO;
import edu.kit.staffManager.api.dto.UserLevel;
import edu.kit.staffManager.logic.StaffManager;
import edu.kit.staffManager.logic.exceptions.UserException;
import edu.kit.staffManager.logic.exceptions.UserLevelException;
import edu.kit.staffManager.logic.model.userLevel.EmployeeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("staffManager")
@CrossOrigin(origins = "http://localhost:4200")
public class StaffManagerController {

    private final StaffManager manager;

    @Autowired
    public StaffManagerController(StaffManager manager) {
        this.manager = manager;
    }

    @GetMapping(path = "getTypes")
    public List<String> getTypes() {
        return manager.getTypes();
    }

    @PostMapping(path = "login")
    public UUID login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            return manager.login(username, password);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping(path = "getRanksOfType")
    public List<RankOutput> getRanksOfType(@RequestParam("type") String type) {
        try {
            return manager.getRanksOfType(type);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping(path = "addType")
    public void addType(@RequestParam("typeName") String name) {
        try {
            manager.addType(name);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping(path = "addRank")
    public void addRankToType(@RequestParam("typeName") String type, @RequestParam("rankName") String rank) {
        try {
            manager.addRank(type, rank);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping(path = "changeImportance")
    public int changeImportance(@RequestParam("typeName") String type, @RequestParam("rankName") String rank, @RequestParam("importance") int importance) {
        try {
            return manager.changeImportance(type, rank, importance);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping(path = "swapImportance")
    public void swapImportance(@RequestParam("typeName") String type, @RequestParam("rankName1") String rank1, @RequestParam("rankName2") String rank2) {
        try {
            manager.swapImportance(type, rank1, rank2);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping(path = "insertImportance")
    public void insertImportance(@RequestParam("typeName") String type, @RequestParam("rankName") String rank, @RequestParam("importance") int importance) {
        try {
             manager.insertImportance(type, rank, importance);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping(path = "addUser")
    public void addUser(@RequestBody UserDTO user) {
        try {
            manager.addUser(user);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping(path = "getUserLevel/{id}")
    public UserLevel getUserLevel(@PathVariable("id") UUID id) {
        try {
            return manager.getUserLevel(id);
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @GetMapping(path = "typeExist")
    public boolean typeExist(@RequestParam("type") String employeeType) {
        return manager.employeeTypeExists(employeeType);
    }

    @GetMapping(path = "rankExist")
    public boolean rankExist(@RequestParam("type") String employeeType, @RequestParam("rank") String employeeRank) {
        return manager.employeeRankExists(employeeType, employeeRank);
    }

    @GetMapping(path = "rankHigherEquals")
    public boolean rankHigherEquals(@RequestParam("minType") String minEmployeeType,
                                    @RequestParam("minRank") String minEmployeeRank,
                                    @RequestParam("actualType") String actualEmployeeType,
                                    @RequestParam("actualRank") String actualEmployeeRank) {
        try {
            return manager.userLevelHigherEquals(minEmployeeType, minEmployeeRank, actualEmployeeType, actualEmployeeRank);
        } catch (UserLevelException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
