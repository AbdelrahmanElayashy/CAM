//package edu.kit.stateManager.logic.service;
//
//import edu.kit.stateManager.api.inputClasses.*;
//import edu.kit.stateManager.api.outputClasses.AdditionalInformationOutput;
//import edu.kit.stateManager.api.outputClasses.CurrentStateOutput;
//import edu.kit.stateManager.api.outputClasses.HistoryOutput;
//import edu.kit.stateManager.logic.model.DefectPriority;
//import edu.kit.stateManager.logic.model.StateNames;
//import edu.kit.stateManager.logic.service.exceptions.*;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Disabled
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class StateHandlerTestDependedOnDB {
//
//    StateHandlerInterface stateHandler;
//
//    UUID medicalDevice1 = UUID.fromString("12f9a194-b05d-4d96-b404-7162c28a6e96");
//    UUID medicalDevice2 = UUID.fromString("18dcbebd-25b5-4932-83f3-1345412ce15a");
//    UUID user1 = UUID.fromString("fa527b41-7fea-4718-a48f-c0899f4990a5");
//    UUID user2 = UUID.fromString("e42fc33f-1bbd-4391-b6d8-3ed5e9875e04");
//
//    @Autowired
//    StateHandlerTestDependedOnDB(StateHandler stateHandler) {
//        this.stateHandler = stateHandler;
//    }
//
//    @BeforeEach
//    void setup() {
//        try {
//            stateHandler.addInitialState(new StateInput(medicalDevice1, user1, StateNames.AVAILABLE));
//            stateHandler.addInitialState(new StateInput(medicalDevice2, user1, StateNames.BOUGHT));
//        } catch (IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        } catch (DeviceException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        try {
//            stateHandler.deleteState(new DeleteInput(medicalDevice1, user1));
//            stateHandler.deleteState(new DeleteInput(medicalDevice2, user1));
//        } catch (DeviceException | IncorrectAttributeException | UserException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @Test
//    void addInitialState() {
//        UUID device = UUID.fromString("fd6b4c5b-61dc-4b67-8e5d-dfe028885c98");
//
//
//        try {
//            stateHandler.addInitialState(new StateInput(device, user1, StateNames.AVAILABLE));
//        } catch (DeviceException | IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        CurrentStateOutput state = null;
//        try {
//            state = stateHandler.getDeviceState(device, user1);
//        } catch (DeviceNotFoundException | IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(state.getDeviceId(), device);
//        assertEquals(state.getState(), StateNames.AVAILABLE);
//        assertEquals(state.getEnteredBy(), user1);
//        try {
//            stateHandler.deleteState(new DeleteInput(device, user1));
//        } catch (DeviceException | IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void deleteState() {
//        try {
//            stateHandler.deleteState(new DeleteInput(medicalDevice1, user1));
//        } catch (DeviceException | IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        try {
//            assertFalse(stateHandler.getDeviceList(user1).contains(medicalDevice1));
//        } catch (IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void changeState() {
//        try {
//            stateHandler.changeState(new StateInput(medicalDevice1, user1, StateNames.IN_USE));
//        } catch (DeviceException | IncorrectAttributeException | UserException | StateException e) {
//            fail(e.getMessage());
//        }
//        CurrentStateOutput state = null;
//        try {
//            state = stateHandler.getDeviceState(medicalDevice1, user1);
//        } catch (DeviceNotFoundException |IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(state.getEnteredBy(), user1);
//        assertEquals(state.getState(), StateNames.IN_USE);
//        assertEquals(state.getDeviceId(), medicalDevice1);
//    }
//
//    @Test
//    void setPriority() {
//        CurrentStateOutput state = null;
//        try {
//            state = stateHandler.getDeviceState(medicalDevice1, user1);
//        } catch (DeviceNotFoundException |IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(state.getDefectPriority(), DefectPriority.LOW);
//        try {
//            stateHandler.setPriority(new DefectPriorityInput(medicalDevice1, user1, DefectPriority.HIGH));
//            state = stateHandler.getDeviceState(medicalDevice1, user1);
//        } catch (DeviceException | IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(state.getDefectPriority(), DefectPriority.HIGH);
//    }
//
//    @Test
//    void setAvailabilityDate() {
//        LocalDate date = LocalDate.now();
//        CurrentStateOutput state = null;
//        try {
//            stateHandler.changeState(new StateInput(medicalDevice1, user1, StateNames.DEFECT_INSPECTION));
//            stateHandler.changeState(new StateInput(medicalDevice1, user1, StateNames.DEFECT_OWN_REPAIR));
//            stateHandler.setAvailabilityDate(new AvailabilityDateInput(medicalDevice1, user1, date));
//            state = stateHandler.getDeviceState(medicalDevice1, user1);
//        } catch (DeviceException | IncorrectAttributeException | UserException | StateException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(state.getAvailabilityDate(), date);
//    }
//
//    @Test
//    void setAvailabilityDateError() {
//        assertThrows(StateException.class, () -> {
//            stateHandler.setAvailabilityDate(new AvailabilityDateInput(medicalDevice1, user1, LocalDate.now()));
//        });
//    }
//
//    @Test
//    void addAdditionalInformation() {
//        CurrentStateOutput stateBefore = null;
//        CurrentStateOutput stateAfter = null;
//        try {
//            stateBefore = stateHandler.getDeviceState(medicalDevice1, user1);
//            stateHandler.addAdditionalInformation(new AdditionalInformationInput(medicalDevice1, user1, "This is the test information"));
//            stateAfter = stateHandler.getDeviceState(medicalDevice1, user1);
//        } catch (DeviceException | IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        assertTrue(stateBefore.getAdditionalInformation().isEmpty());
//        AdditionalInformationOutput information = stateAfter.getAdditionalInformation().get(0);
//        assertEquals(information.getInformation(), "This is the test information");
//    }
//
//    @Test
//    void getDeviceList() {
//        List<UUID> deviceList = null;
//        try {
//            deviceList = stateHandler.getDeviceList(user1);
//        } catch (IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        if (!deviceList.contains(medicalDevice1) || !deviceList.contains(medicalDevice2)) {
//            fail("Does not show all medical devices");
//        }
//    }
//
//    @Test
//    void getDeviceStateSuccess() {
//        CurrentStateOutput state = null;
//        try {
//            state = stateHandler.getDeviceState(medicalDevice1, user1);
//        } catch (DeviceNotFoundException |IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(state.getDeviceId(), medicalDevice1);
//        assertEquals(state.getState(), StateNames.AVAILABLE);
//        assertEquals(state.getEnteredBy(), user1);
//        try {
//            state = stateHandler.getDeviceState(medicalDevice2, user1);
//        } catch (DeviceNotFoundException |IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(state.getDeviceId(), medicalDevice2);
//        assertEquals(state.getState(), StateNames.BOUGHT);
//        assertEquals(state.getEnteredBy(), user1);
//    }
//
//    @Test
//    void getDeviceState() {
//        assertThrows(DeviceNotFoundException.class, () -> {
//            stateHandler.getDeviceState(UUID.randomUUID(), user1);
//        });
//    }
//
//    @Test
//    void getStateHistoryNoDates() {
//        HistoryOutput output = null;
//        try {
//            output = stateHandler.getStateHistory(new ShowHistoryInput(medicalDevice1, user1, null, null));
//        } catch (DeviceNotFoundException |IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(output.getStates().get(0).getState(), StateNames.AVAILABLE);
//    }
//
//    @Test
//    void getStateHistoryDates() {
//        HistoryOutput output = null;
//        try {
//            output = stateHandler.getStateHistory(new ShowHistoryInput(medicalDevice1, user1, LocalDate.now().minusDays(1), LocalDate.now()));
//        } catch (DeviceNotFoundException |IncorrectAttributeException | UserException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(output.getStates().get(0).getState(), StateNames.AVAILABLE);
//    }
//}