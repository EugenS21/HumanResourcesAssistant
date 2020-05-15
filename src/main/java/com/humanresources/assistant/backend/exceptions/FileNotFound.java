package com.humanresources.assistant.backend.exceptions;

public class FileNotFound extends Exception {

    public FileNotFound() {
    }

    public FileNotFound(String message) {
        super(message);
    }
}
