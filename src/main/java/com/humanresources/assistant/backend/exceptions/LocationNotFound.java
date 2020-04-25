package com.humanresources.assistant.backend.exceptions;

public class LocationNotFound extends Exception {

    public LocationNotFound() {
        super();
    }

    public LocationNotFound(String message) {
        super(message);
    }
}
