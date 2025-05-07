package com.memory.hrs.Utils;

public class CallInfo {
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

    public CallInfo(CallType callType, long duration, String targetNumber) {
        this.callType = callType;
        this.duration = duration;
        this.targetNumber = targetNumber;
    }
}
