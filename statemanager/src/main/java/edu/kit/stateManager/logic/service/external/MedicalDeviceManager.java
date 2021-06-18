package edu.kit.stateManager.logic.service.external;

import edu.kit.stateManager.logic.service.exceptions.BooleanResponse;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

public class MedicalDeviceManager implements MedicalDeviceClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public BooleanResponse medicalDeviceExists(UUID deviceId) {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        try {
             ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/medicalDevice/medical-device/" + deviceId.toString(), HttpMethod.GET, entity, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.CONFLICT) ) {
                return new BooleanResponse(false,"The medical device does not exist in MedicalDevice Database");
            } else {
                return new BooleanResponse(false,"Error: " + e.getStatusCode());
            }
        } catch (ResourceAccessException e) {
            return new BooleanResponse(false, "Could not connect to medical device microservice");
        }
        return new BooleanResponse(true, "All fine");
    }
}
