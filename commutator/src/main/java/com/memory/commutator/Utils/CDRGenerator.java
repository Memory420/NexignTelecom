package com.memory.commutator.Utils;

import com.memory.commutator.Models.Abonent;
import com.memory.commutator.Models.CDRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CDRGenerator {

    private final Random random = new Random();

    public List<CDRecord> generateRandomCall(List<Abonent> abonents) {
        List<CDRecord> result = new ArrayList<>();

        Abonent caller = abonents.get(random.nextInt(abonents.size()));
        Abonent receiver;
        do {
            receiver = abonents.get(random.nextInt(abonents.size()));
        } while (receiver.equals(caller));

        CallType callType = random.nextBoolean() ? CallType.INCOMING : CallType.OUTGOING;

        LocalDateTime start = LocalDateTime.now().minusYears(1);
        LocalDateTime end = start.plusSeconds(30 + random.nextInt(300));

        if (start.toLocalDate().isBefore(end.toLocalDate())) {

            LocalDateTime midnight = start.toLocalDate().plusDays(1).atStartOfDay();

            result.add(new CDRecord(null, callType, caller, receiver, start, midnight.minusSeconds(1)));
            result.add(new CDRecord(null, callType, caller, receiver, midnight, end));
        } else {
            result.add(new CDRecord(null, callType, caller, receiver, start, end));
        }

        return result;
    }
}
