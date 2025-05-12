package com.memory.commutator.Util;

import com.memory.commutator.Model.Abonent;
import com.memory.commutator.Model.ActiveCall;
import com.memory.commutator.Model.CDRecord;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Симулирует звонки: создаёт, буферизует и отправляет CDR-записи при достижении заданного объёма.
 */
public class CallWorker implements VirtualTimeWorker {
    private final List<Abonent> generalAbonents;

    private final List<CDRecord> cdrBuffer = new ArrayList<>();

    private final Random random = new Random();
    private ActiveCall currentCall = null;

    private static final int MIN_DURATION = 15;
    private static final double END_CHANCE = 0.05;
    private static final double START_CHANCE_DAY = 0.00005;
    private static final double START_CHANCE_NIGHT = 0.00001;

    public CallWorker(List<Abonent> generalAbonents) {
        this.generalAbonents = generalAbonents;
    }
    public void cheatButton() {                                        // dev метод, потом удалить
        Abonent caller = pickRandom(generalAbonents);
        Abonent receiver = pickRandomExcluding(generalAbonents, caller);

        generalAbonents.remove(caller);
        generalAbonents.remove(receiver);

        LocalDateTime start = LocalDateTime.of(2024, 4, 18, 23, 59, 40);
        LocalDateTime end = start.plusSeconds(40);


        List<CDRecord> recs = generateCdRecords(caller, receiver, start, end);
        cdrBuffer.addAll(recs);

        generalAbonents.add(caller);
        generalAbonents.add(receiver);
    }

    @Override
    public void onTimeWork(LocalDateTime now) {
        if (currentCall == null) {
            // попытка начать новый звонок
            double chance = (now.getHour() >= 8) ? START_CHANCE_DAY : START_CHANCE_NIGHT;

            if (random.nextDouble() < chance && generalAbonents.size() >= 2) {
                Abonent caller   = pickRandom(generalAbonents);
                Abonent receiver = pickRandomExcluding(generalAbonents, caller);

                generalAbonents.remove(caller);
                generalAbonents.remove(receiver);

                currentCall = new ActiveCall(caller, receiver, now);
            }
        } else {
            currentCall.increaseDuration();
            // попытка завершить звонок и проверка на длительность
            if (currentCall.getDuration() >= MIN_DURATION && random.nextDouble() < END_CHANCE) {
                List<CDRecord> recs = generateCdRecords(
                        currentCall.getCaller(),
                        currentCall.getReceiver(),
                        currentCall.getStartTime(),
                        now
                );
                cdrBuffer.addAll(recs);

                generalAbonents.add(currentCall.getCaller());
                generalAbonents.add(currentCall.getReceiver());

                currentCall = null;
            }
        }
    }

    public List<CDRecord> getCdrBuffer() {
        return cdrBuffer;
    }

    // разделение CDR если есть пересечение полночи
    private List<CDRecord> generateCdRecords(Abonent caller,
                                             Abonent receiver,
                                             LocalDateTime start,
                                             LocalDateTime end) {
        List<CDRecord> out = new ArrayList<>();
        LocalDateTime segmentStart = start;

        while (!segmentStart.toLocalDate().equals(end.toLocalDate())) {
            LocalDateTime midnight = segmentStart.toLocalDate().atTime(LocalTime.MAX);
            out.addAll(makeMirrorRecords(caller, receiver, segmentStart, midnight));
            segmentStart = midnight.plusSeconds(1);
        }


        out.addAll(makeMirrorRecords(caller, receiver, segmentStart, end));
        return out;
    }

    // зеркальная запись если ромашка-ромашка
    private List<CDRecord> makeMirrorRecords(Abonent caller,
                                             Abonent receiver,
                                             LocalDateTime segStart,
                                             LocalDateTime segEnd) {
        List<CDRecord> list = new ArrayList<>();

        list.add(new CDRecord(CallType.OUTGOING, caller.getNumber(), receiver.getNumber(), segStart, segEnd));
        list.add(new CDRecord(CallType.INCOMING, receiver.getNumber(), caller.getNumber(), segStart, segEnd));

        return list;
    }

    private Abonent pickRandom(List<Abonent> list) {
        return list.get(random.nextInt(list.size()));
    }

    private Abonent pickRandomExcluding(List<Abonent> list, Abonent ex) {
        List<Abonent> filtered = new ArrayList<>(list);
        filtered.remove(ex);
        return pickRandom(filtered);
    }
}
