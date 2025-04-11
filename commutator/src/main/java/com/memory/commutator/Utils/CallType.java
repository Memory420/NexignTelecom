package com.memory.commutator.Utils;

public enum CallType {
    INCOMING("01"),
    OUTGOING("02");

    private final String code;

    CallType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CallType fromCode(String code) {
        for (CallType type : CallType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CallType: " + code);
    }
}
