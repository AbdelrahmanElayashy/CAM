package edu.kit.stateManager.api;

import edu.kit.stateManager.infrastructure.inputClasses.*;
import edu.kit.stateManager.infrastructure.outputClasses.CurrentStateOutput;
import edu.kit.stateManager.infrastructure.outputClasses.HistoryOutput;
import edu.kit.stateManager.logic.model.state.StateNames;
import edu.kit.stateManager.logic.service.StateHandler;
import edu.kit.stateManager.logic.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@RequestMapping("stateHandler")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class StateManagerControllerREST {

    private final StateHandler stateHandler;

    @Autowired
    public StateManagerControllerREST(StateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }

    @PostMapping(path = "addInitialState")
    public UUID addInitialState(@RequestParam("userId") UUID userId, @RequestBody StateInput stateInput) {
        stateInput.setUserId(userId);
        try {
            return stateHandler.addInitialState(stateInput);
        } catch (DeviceException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "removeState/{deviceId}")
    public UUID removeState(@PathVariable("deviceId") UUID deviceId, @RequestParam("userId") UUID userId) {
        try {
            return stateHandler.removeState(new DeleteInput(deviceId, userId));
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (DeviceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "deleteHistory/{deviceId}")
    public UUID deleteHistory(@PathVariable("deviceId") UUID deviceId, @RequestParam("userId") UUID userId) {
        try {
            return stateHandler.deleteHistory(new DeleteInput(deviceId, userId));
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (DeviceNotFoundException | StateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "changeState/{deviceId}")
    public UUID changeState(@PathVariable("deviceId") UUID id, @RequestParam("userId") UUID userId, @RequestBody StateInput stateInput) {
        stateInput.setDeviceId(id);
        stateInput.setUserId(userId);
        try {
            return stateHandler.changeState(stateInput);
        } catch (DeviceException | StateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (UserException | UserLevelException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "setToReserved/{deviceId}")
    public UUID setToReserved(@PathVariable("deviceId") UUID deviceId, @RequestParam("userId") UUID userId) {
        try {
            return stateHandler.setToReserved(deviceId, userId);
        } catch (DeviceNotFoundException | StateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @GetMapping(path = "getState/{deviceId}")
    public CurrentStateOutput getState(@PathVariable("deviceId") UUID deviceId, @RequestParam("userId") UUID userId) {
        try {
            return stateHandler.getDeviceState(deviceId, userId);
        } catch (DeviceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "showHistory/{deviceId}")
    public HistoryOutput showHistory(@PathVariable("deviceId") UUID deviceId, @RequestParam("userId") UUID userId, @RequestBody ShowHistoryInput historyInput) {
        historyInput.setDeviceId(deviceId);
        historyInput.setUserId(userId);
        try {
            return stateHandler.getStateHistory(historyInput);
        } catch (DeviceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "setDefectPriority/{deviceId}")
    public UUID setDefectPriority(@PathVariable("deviceId") UUID deviceId, @RequestParam("userId") UUID userId, @RequestBody DefectPriorityInput priorityInput) {
        priorityInput.setDeviceId(deviceId);
        priorityInput.setUserId(userId);
        try {
            return stateHandler.setPriority(priorityInput);
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (DeviceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "setAvailabilityDate/{deviceId}")
    public UUID setAvailabilityDate(@PathVariable("deviceId") UUID deviceId, @RequestParam("userId") UUID userId, @RequestBody AvailabilityDateInput dateInput) {
        dateInput.setDeviceId(deviceId);
        dateInput.setUserId(userId);
        try {
            return stateHandler.setAvailabilityDate(dateInput);
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (DeviceNotFoundException | StateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "addAdditionalInformation/{deviceId}")
    public UUID addAdditionalInformation(@PathVariable("deviceId") UUID id, @RequestParam("userId") UUID userId, @RequestBody AdditionalInformationInput informationInput) {
        informationInput.setDeviceId(id);
        informationInput.setUserId(userId);
        try {
            return stateHandler.addAdditionalInformation(informationInput);
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (DeviceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(path = "getPossibleNextStates/{deviceId}")
    public List<StateNames> getPossibleNextStates(@PathVariable("deviceId") UUID deviceId, @RequestParam("userId") UUID userId) {
        try {
            return stateHandler.getPossibleNextStates(deviceId, userId);
        } catch (DeviceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "getStatesByDeviceIds")
    @ResponseBody
    public List<CurrentStateOutput> getFilteredDevicesByIds(@RequestParam("userId") UUID userId, @RequestParam("deviceIds") UUID[] deviceIds, @RequestBody(required = false) FilteredDeviceListInput filterInput) {
        if (filterInput == null) {
            filterInput = new FilteredDeviceListInput();
        }
        filterInput.setUserId(userId);
        filterInput.setDevices(deviceIds);
        try {
            return stateHandler.getFilteredDevicesByID(filterInput);
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(path = "resetStatePermissionsByType")
    public String uploadStatePermissionFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("userId") UUID userId) {
        try {
            String type = stateHandler.uploadStatePermissionFile(multipartFile, userId);
            return "Upload and read of file " + multipartFile.getOriginalFilename() + " was successful.\nState transition permissions for type " + type + " are reset.";
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PostMapping(path = "resetCommandPermissions")
    public String uploadCommandPermissionFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("userId") UUID userId) {
        try {
            stateHandler.uploadCommandPermissionFile(multipartFile, userId);
            return "Upload and read of file " + multipartFile.getOriginalFilename() + " was successful.\nCommand permissions are reset.";
        } catch (ConnectionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IncorrectAttributeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UserException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
