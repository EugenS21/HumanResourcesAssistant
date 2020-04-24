package com.humanresources.assistant.backend.exceptions;

public class DepartmentNotFound extends Exception {

    public DepartmentNotFound() {
        super();
    }

    public DepartmentNotFound(String message) {
        super(message);
    }
}
