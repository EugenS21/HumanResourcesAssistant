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
}
