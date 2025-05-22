package com.demo.app.enums;

public enum Location {
    NY("NewYork"),
    CA("California");

    private String description;

    Location(String description) {
        this.description = description;
    }
}
