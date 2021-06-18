package edu.kit.stateManager.logic.service.exceptions;

public class BooleanResponse {

    private final boolean value;
    private final String message;

    public BooleanResponse(boolean value, String message) {
        this.value = value;
        this.message = message;
    }

    public boolean getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
