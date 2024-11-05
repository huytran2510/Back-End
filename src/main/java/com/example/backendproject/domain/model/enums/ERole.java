package com.example.backendproject.domain.model.enums;

public enum ERole {
    CUSTOMER("CUSTOMER"), ADMIN("ADMIN"), EMPLOYEE("EMPLOYEE"), SERVICE_STAFF("CUSTOMER_SERVICE_STAFF");
    private final String text;

    private ERole(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
