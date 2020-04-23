package com.humanresources.assistant.backend.exceptions;

public class EmployeeNotFound extends Exception {

    public EmployeeNotFound() {
        super();
    }

    public EmployeeNotFound(String message) {
        super(message);
    }
}
