package com.memory.commutator.Model;

import com.memory.commutator.Util.CallType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CDRecord {
    private CallType callType;

    private String caller;
    private String receiver;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public CDRecord() {
    }

    public CDRecord(CallType callType, String caller, String receiver, LocalDateTime startTime, LocalDateTime endTime) {
        this.callType = callType;
        this.caller = caller;
        this.receiver = receiver;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCaller() {
        return caller;
    }

    public String getReceiver() {
        return receiver;
    }

    public CallType getCallType() {
        return callType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String toCsv(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return String.format("%s, %s, %s, %s, %s",
                callType.getCode(),
                caller,
                receiver,
                startTime.format(formatter),
                endTime.format(formatter));
    }

    @Override
    public String toString() {
        return toCsv();
    }
}
