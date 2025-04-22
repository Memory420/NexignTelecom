package com.memory.brt.Model;

import com.memory.brt.Util.CallType;
import com.memory.brt.Util.CallTypeConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class CDRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = CallTypeConverter.class)
    private CallType callType;

    private String caller;
    private String receiver;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public CDRecord(CallType callType, String caller, String receiver, LocalDateTime startTime, LocalDateTime endTime) {
        this.callType = callType;
        this.caller = caller;
        this.receiver = receiver;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CDRecord() {
    }

    public Long getId() {
        return id;
    }

    public CallType getCallType() {
        return callType;
    }

    public String getCaller() {
        return caller;
    }

    public String getReceiver() {
        return receiver;
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
