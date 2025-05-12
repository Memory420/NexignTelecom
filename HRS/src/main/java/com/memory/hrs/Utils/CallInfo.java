package com.memory.hrs.Utils;

public class CallInfo {
    private final String phoneNumber;
    private final CallType callType;
    private final long duration;
    private final String targetNumber;

    public CallType getCallType() {
        return callType;
    }

    public long getDuration() {
        return duration;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CallInfo(String phoneNumber, CallType callType, long duration, String targetNumber) {
        this.phoneNumber = phoneNumber;
        this.callType = callType;
        this.duration = duration;
        this.targetNumber = targetNumber;
    }
}
