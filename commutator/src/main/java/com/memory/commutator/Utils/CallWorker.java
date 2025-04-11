package com.memory.commutator.Utils;

import com.memory.commutator.Models.Abonent;
import com.memory.commutator.Models.CDRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CallWorker implements VirtualTimeWorker{
    private final List<Abonent> freeAbonents;
    private final List<CDRecord> cdrBuffer;
    private final Random random = new Random();
    private ActiveCall currentCall = null;

    private final double chanceToEndCall = 0.05;

    public CallWorker(List<Abonent> freeAbonents) {
        this.freeAbonents = freeAbonents;
        cdrBuffer = new ArrayList<>();
    }

    @Override
    public void onTimeWork(LocalDateTime time) {
        if (currentCall == null) {
            double chance = getTimeBasedChance(time);
            if (random.nextDouble() < chance && freeAbonents.size() >= 2) {
                Abonent caller = getRandomFree();
                Abonent receiver = getRandomFreeExcluding(caller);

                if (caller != null && receiver != null) {
                    freeAbonents.remove(caller);
                    freeAbonents.remove(receiver);
                    currentCall = new ActiveCall(caller, receiver, time);
                    System.out.println("Звонок от " + caller.getNumber() + " -> " + receiver.getNumber());
                }
            }
        } else {
            currentCall.increaseDuration();
            if (currentCall.getDuration() >= 15 && random.nextDouble() < chanceToEndCall) {
                CDRecord record = new CDRecord(null, CallType.OUTGOING,
                        currentCall.getCaller(),
                        currentCall.getReceiver(),
                        currentCall.getStartTime(),
                        time);
                cdrBuffer.add(record);
                freeAbonents.add(currentCall.getCaller());
                freeAbonents.add(currentCall.getReceiver());
                currentCall = null;
            }
        }
    }

    private Abonent getRandomFree() {
        if (freeAbonents.isEmpty()){
            return null;
        }
        return freeAbonents.get(random.nextInt(freeAbonents.size()));
    }

    private Abonent getRandomFreeExcluding(Abonent exclude) {
        List<Abonent> filtered = freeAbonents.stream()
                .filter(a -> !a.equals(exclude))
                .toList();
        if (filtered.isEmpty()) {
            return null;
        }
        return filtered.get(random.nextInt(filtered.size()));
    }
    public List<CDRecord> getCdrBuffer(){
        return cdrBuffer;
    }
    private double getTimeBasedChance(LocalDateTime time){
        int hour = time.getHour();
        if (hour >= 8 && hour <= 23){
            return 0.00005;
        } else {
            return 0.00001;
        }
    }
}
