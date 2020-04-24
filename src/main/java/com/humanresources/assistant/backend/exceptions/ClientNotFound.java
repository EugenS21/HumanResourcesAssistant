package com.humanresources.assistant.backend.exceptions;

public class ClientNotFound extends Exception {

    public ClientNotFound() {
    }

    public ClientNotFound(String message) {
        super(message);
    }
}
