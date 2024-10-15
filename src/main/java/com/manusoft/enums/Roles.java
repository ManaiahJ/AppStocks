package com.manusoft.enums;

public enum Roles {
    ADMIN("ADMIN"),
    USER("USER");
    private String value;
    Roles(String value) {
        this.value = value;
    }
    public String getName() {
        return value;
    }
    public static Roles getType(String name) {
        Roles[] values = Roles.values();
        for (Roles entityType : values) {
            if (entityType.name().equalsIgnoreCase(name)) {
                return entityType;
            }
        }

        return null;
    }
}
