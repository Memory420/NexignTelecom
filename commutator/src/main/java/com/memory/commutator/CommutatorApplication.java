package com.memory.commutator;

import com.memory.commutator.Models.Abonent;
import com.memory.commutator.Models.CDRecord;
import com.memory.commutator.Repositories.AbonentRepository;
import com.memory.commutator.Repositories.CDRecordRepository;
import com.memory.commutator.Service.CallSimulationService;
import com.memory.commutator.Utils.CallWorker;
import com.memory.commutator.Utils.VirtualTimeWorker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SpringBootApplication
public class CommutatorApplication implements CommandLineRunner {
	public final static List<Abonent> abonents = Arrays.asList(
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
	);

	private final AbonentRepository abonentRepository;
	private final CallSimulationService callSimulationService;
	private final CDRecordRepository cdRecordRepository;


	public CommutatorApplication(AbonentRepository abonentRepository, CallSimulationService callSimulationService, CDRecordRepository cdRecordRepository) {
		this.abonentRepository = abonentRepository;
        this.callSimulationService = callSimulationService;
        this.cdRecordRepository = cdRecordRepository;
    }

	@Override
	public void run(String... args) {
		System.out.println("Commutator started");

		abonentRepository.saveAll(abonents);

		List<Abonent> abonentsFromDb = abonentRepository.findAll();
		List<CDRecord> cdrRecords = callSimulationService.runSimulation(abonentsFromDb, 5, 7);

		for (CDRecord cdr : cdrRecords) {
			System.out.println(cdr);
		}

		cdRecordRepository.saveAll(cdrRecords);
		System.out.println("Всего отчётов: " + cdrRecords.size());
		System.out.println("Симуляция завершена");
	}

	public static void main(String[] args) {
		SpringApplication.run(CommutatorApplication.class, args);
	}
}

