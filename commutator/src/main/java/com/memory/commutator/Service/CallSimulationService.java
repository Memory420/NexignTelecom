package com.memory.commutator.Service;

import com.memory.commutator.Models.Abonent;
import com.memory.commutator.Models.CDRecord;
import com.memory.commutator.Utils.CallWorker;
import com.memory.commutator.Utils.VirtualTimeWorker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CallSimulationService {
    public List<CDRecord> runSimulation(List<Abonent> abonents, int workerCount, int days) {
        List<CallWorker> workers = new ArrayList<>();
        for (int i = 0; i < workerCount; i++) {
            workers.add(new CallWorker(abonents));
        }

        LocalDateTime virtualTime = LocalDateTime.now().minusYears(1);
        LocalDateTime end = virtualTime.plusDays(days);

        while (virtualTime.isBefore(end)) {
            for (VirtualTimeWorker worker : workers) {
                worker.onTimeWork(virtualTime);
            }
            virtualTime = virtualTime.plusSeconds(1);
        }

        List<CDRecord> result = new ArrayList<>();
        for (CallWorker worker : workers) {
            result.addAll(worker.getCdrBuffer());
        }

        result.sort(Comparator.comparing(CDRecord::getStartTime));
        return result;
    }
}
