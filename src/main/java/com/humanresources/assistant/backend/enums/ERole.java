package com.humanresources.assistant.backend.enums;

public enum ERole {
    ADMIN,
    HR,
    HR_ASSISTANT,
    USER;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public boolean equals(String role) {
        return this.toString().toLowerCase().equals(role.toLowerCase().trim());
    }
}
