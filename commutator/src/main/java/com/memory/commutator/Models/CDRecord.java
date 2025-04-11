package com.memory.commutator.Models;

import com.memory.commutator.Utils.CallType;
import com.memory.commutator.Utils.CallTypeConverter;
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

    @ManyToOne
    private Abonent caller;

    @ManyToOne
    private Abonent receiver;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public CDRecord() {
    }

    public CDRecord(Long id, CallType callType, Abonent caller, Abonent receiver, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.callType = callType;
        this.caller = caller;
        this.receiver = receiver;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public CallType getCallType() {
        return callType;
    }

    public Abonent getCaller() {
        return caller;
    }

    public Abonent getReceiver() {
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
                caller.getNumber(),
                receiver.getNumber(),
                startTime.format(formatter),
                endTime.format(formatter));
    }

    public static void main(String[] args) {
        Abonent abonent1 = new Abonent("79930125779");
        Abonent abonent2 = new Abonent("79045404032");
        CDRecord record = new CDRecord(1L, CallType.INCOMING, abonent1, abonent2, LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
        System.out.println(record.toCsv());
    }

    @Override
    public String toString() {
        return toCsv();
    }
}
