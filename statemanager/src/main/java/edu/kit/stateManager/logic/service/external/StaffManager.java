package edu.kit.stateManager.logic.service.external;

import edu.kit.stateManager.logic.model.permission.UserLevel;
import edu.kit.stateManager.logic.service.exceptions.ConnectionException;
import edu.kit.stateManager.logic.service.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class StaffManager implements StaffClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserLevel getUserLevel(UUID userId) throws ConnectionException, UserException {

        ResponseEntity<UserLevel> response;
        try {
            response = restTemplate.getForEntity("http://localhost:8082/staffManager/getUserLevel/" + userId.toString(), UserLevel.class);
        }  catch (ResourceAccessException e) {
            throw new ConnectionException("Could not connect to staff manager microservice");
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.CONFLICT) ) {
                throw new UserException("User does not exist");
            } else {
                throw new ConnectionException("Error: " + e.getStatusCode());
            }
        }
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }
        throw new ConnectionException("Error: " + response.getStatusCode());
    }

    @Override
    public boolean employeeTypeExists(String employeeType) throws ConnectionException {
        ResponseEntity<Boolean> response;
        try {
            response = restTemplate.getForEntity("http://localhost:8082/staffManager/typeExist?type=" + employeeType, Boolean.class);
        }  catch (ResourceAccessException e) {
            throw new ConnectionException("Could not connect to staff manager microservice");
        } catch (HttpClientErrorException e) {
            throw new ConnectionException("Error: " + e.getStatusCode() + " in employeeTypeExists");
        }
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            Boolean result = response.getBody();
            if (result == null) {
                throw new ConnectionException("Error: getEmployeeType returned null");
            }
            return response.getBody();
        }
        throw new ConnectionException("Error: " + response.getStatusCode());
    }

    @Override
    public boolean employeeRankExists(UserLevel userLevel) throws ConnectionException {
        ResponseEntity<Boolean> response;
        try {
            response = restTemplate.getForEntity("http://localhost:8082/staffManager/rankExist?type=" + userLevel.getEmployeeType() + "&rank=" + userLevel.getEmployeeRank(), Boolean.class);
        }  catch (ResourceAccessException e) {
            throw new ConnectionException("Could not connect to staff manager microservice");
        } catch (HttpClientErrorException e) {
            throw new ConnectionException("Error: " + e.getStatusCode() + " in employeeRankExists");
        }
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            Boolean result = response.getBody();
            if (result == null) {
                throw new ConnectionException("Error: getEmployeeRank returned null");
            }
            return response.getBody();
        }
        throw new ConnectionException("Error: " + response.getStatusCode());
    }

    @Override
    public boolean rankHigherEqualThan(UserLevel minUserLevel, UserLevel actualUserLevel) throws ConnectionException {
        ResponseEntity<Boolean> response;
        try {
            response = restTemplate.getForEntity("http://localhost:8082/staffManager/rankHigherEquals?minType=" + minUserLevel.getEmployeeType() +
                    "&minRank=" + minUserLevel.getEmployeeRank() + "&actualType=" + actualUserLevel.getEmployeeType() + "&actualRank=" + actualUserLevel.getEmployeeRank(), Boolean.class);
        }  catch (ResourceAccessException e) {
            throw new ConnectionException("Could not connect to staff manager microservice");
        } catch (HttpClientErrorException e) {
            throw new ConnectionException("Error: " + e.getStatusCode() + " in rankHigherEqual");
        }
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            Boolean result = response.getBody();
            if (result == null) {
                throw new ConnectionException("Error: getEmployeeRank returned null");
            }
            return response.getBody();
        }
        throw new ConnectionException("Error: " + response.getStatusCode());
    }
}
