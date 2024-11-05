package com.example.backendproject.domain.model.enums;

public enum Status {
    PENDING("PENDING"), DELIVERED("DELIVERED"), SHIPPING("SHIPPING");
    private final String text;

    private Status(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
