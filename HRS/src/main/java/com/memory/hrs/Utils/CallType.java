package com.memory.hrs.Utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CallType {
    OUTGOING("01"),
    INCOMING("02");

    private final String code;

    CallType(String code) {
        this.code = code;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static CallType fromCode(String code) {
        for (CallType type : CallType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CallType: " + code);
    }
}
