package com.memory.commutator.Service;

import com.memory.commutator.Model.Abonent;
import com.memory.commutator.Model.CDRecord;
import com.memory.commutator.Util.CallWorker;
import com.memory.commutator.Util.Operator;
import com.memory.commutator.Util.VirtualTimeWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CallSimulation {
    public static final List<Abonent> ABONENTS = List.of(
            new Abonent("79990111111", Operator.ROMASHKA),
            new Abonent("79990222222", Operator.ROMASHKA),
            new Abonent("79990333333", Operator.ROMASHKA),
            new Abonent("79990444444", Operator.ROMASHKA),
            new Abonent("79990555555", Operator.ROMASHKA),
            new Abonent("79990666666", Operator.ROMASHKA),
            new Abonent("79990777777", Operator.ROMASHKA),
            new Abonent("79880111111", Operator.OTHER),
            new Abonent("79880222222", Operator.OTHER),
            new Abonent("79880333333", Operator.OTHER)
    );

    private final RabbitTemplate rabbit;

    public CallSimulation(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

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

        List<CDRecord> all = new ArrayList<>();
        for (CallWorker w : workers) {
            all.addAll(w.getCdrBuffer());
        }
        all.sort(Comparator.comparing(CDRecord::getEndTime).reversed());

        for (int i = 0; i < all.size(); i += 10) {
            List<CDRecord> batch = new ArrayList<>(all.subList(i, Math.min(i+10, all.size())));
            List<CDRecord> dtoBatch = batch.stream()
                    .map(r -> new CDRecord(
                            r.getCallType(),
                            r.getCaller(),
                            r.getReceiver(),
                            r.getStartTime(),
                            r.getEndTime()
                    ))
                    .toList();

            rabbit.convertAndSend("cdr.to.brt", dtoBatch);
        }

        return all;
    }

    public static void main(String[] args) {
        CallWorker cheater = new CallWorker(new ArrayList<>(CallSimulation.ABONENTS));
        cheater.cheatButton();

        List<CDRecord> records = cheater.getCdrBuffer();
        for (CDRecord record : records) {
            System.out.println(record.toCsv());
        }
    }
}
