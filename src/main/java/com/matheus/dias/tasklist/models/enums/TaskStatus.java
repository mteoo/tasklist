package com.matheus.dias.tasklist.models.enums;

public enum TaskStatus {
    NEW(0, "New"),
    PENDING(1, "Pending"),
    DONE(2, "Done");

    private final Integer value;
    private final String description;

    TaskStatus(final Integer value, final String description) {
        this.value = value;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Integer toValue() {
        return value;
    }

    public static TaskStatus fromValue(Integer value) {
        for (TaskStatus status : values()) {
            if (status.toValue().equals(value)) return status;
        }
        throw new IllegalArgumentException(String.format("Enum value is invalid", value));
    }

    public String toString() {
        return value.toString();
    }
}
