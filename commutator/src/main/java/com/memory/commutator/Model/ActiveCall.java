package com.memory.commutator.Model;

import java.time.LocalDateTime;

public class ActiveCall {
    private Abonent caller;
    private Abonent receiver;
    private LocalDateTime startTime;
    private int duration;

    public ActiveCall(Abonent caller, Abonent receiver, LocalDateTime startTime) {
        this.caller = caller;
        this.receiver = receiver;
        this.startTime = startTime;
    }

    public Abonent getCaller() {
        return caller;
    }

    public void setCaller(Abonent caller) {
        this.caller = caller;
    }

    public Abonent getReceiver() {
        return receiver;
    }

    public void setReceiver(Abonent receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void increaseDuration() {
        duration++;
    }
}
