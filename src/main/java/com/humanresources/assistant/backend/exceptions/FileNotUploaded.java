package com.humanresources.assistant.backend.exceptions;

import java.io.IOException;

public class FileNotUploaded extends IOException {

    public FileNotUploaded(String message) {
        super(message);
    }
}
