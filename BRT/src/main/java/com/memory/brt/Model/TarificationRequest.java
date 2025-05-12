package com.memory.brt.Model;

public class TarificationRequest {
    private final String number;
    private final String targetNumber;
    private final Long callSeconds;

    public TarificationRequest(String number, String targetNumber, Long callSeconds) {
        this.number = number;
        this.targetNumber = targetNumber;
        this.callSeconds = callSeconds;
    }

    public String getNumber() {
        return number;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public Long getCallSeconds() {
        return callSeconds;
    }
}
