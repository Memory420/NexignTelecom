package com.memory.commutator.Service;

import com.memory.commutator.Models.Abonent;
import com.memory.commutator.Models.CDRecord;
import com.memory.commutator.Repositories.AbonentRepository;
import com.memory.commutator.Repositories.CDRecordRepository;
import com.memory.commutator.Utils.CDRGenerator;
import com.memory.commutator.Utils.CallWorker;
import com.memory.commutator.Utils.VirtualTimeWorker;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CDRService {

    private final AbonentRepository abonentRepository;
    private final CDRecordRepository cdrrecordRepository;
    private final CDRGenerator generator = new CDRGenerator();

    public CDRService(AbonentRepository abonentRepository, CDRecordRepository cdrrecordRepository) {
        this.abonentRepository = abonentRepository;
        this.cdrrecordRepository = cdrrecordRepository;
    }

    public void generateCDRRecordsAsync(int totalCalls, int threads) {
        List<Abonent> abonents = abonentRepository.findAll();
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        int callsPerThread = totalCalls / threads;

        for (int i = 0; i < threads; i++) {
            executor.execute(() -> {
                for (int j = 0; j < callsPerThread; j++) {
                    List<CDRecord> records = generator.generateRandomCall(abonents);
                    synchronized (cdrrecordRepository) {
                        cdrrecordRepository.saveAll(records);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        executor.shutdown();
    }

    public static void main(String[] args) {
        List<Abonent> abonents = new ArrayList<>(List.of(
                new Abonent("79930125779"),
                new Abonent("79045404032"),
                new Abonent("79021898067"),
                new Abonent("79018917453"),
                new Abonent("79580402301"),
                new Abonent("79812345678"),
                new Abonent("79123456789"),
                new Abonent("79231234567"),
                new Abonent("79451234567"),
                new Abonent("79761234567")
        ));
        List<CallWorker> workers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            workers.add(new CallWorker(abonents));
        }
        LocalDateTime virtualTime = LocalDateTime.now();
        LocalDateTime weekLater = LocalDateTime.now().plusWeeks(1);
        while (virtualTime.isBefore(weekLater)) { // пока не пройдёт неделя
            for (VirtualTimeWorker worker : workers) {
                worker.onTimeWork(virtualTime);
            }
            virtualTime = virtualTime.plusSeconds(1);

        }

        List<CDRecord> cdrRecords = new ArrayList<>();

        for (CallWorker worker : workers) {
            cdrRecords.addAll(worker.getCdrBuffer());
        }
        int i = 0;

//        cdrRecords.sort(Comparator.comparing(cdr -> cdr.getCaller().getNumber()));
//        List<CDRecord> filtered = cdrRecords.stream().filter(a -> a.getCaller().getNumber().equals("79930125779")).toList();

//        for (CDRecord cdr : filtered) {
//            i++;
//            System.out.println(cdr.toString());
//        }

        for (CDRecord cdrRecord : cdrRecords) {
            System.out.println(cdrRecord.toString());
        }
        System.out.println("Всего отчётов: " + i);
        System.out.println("Симуляция завершена");
    }
}
