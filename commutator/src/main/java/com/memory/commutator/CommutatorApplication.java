package com.memory.commutator;

import com.memory.commutator.Models.Abonent;
import com.memory.commutator.Repositories.AbonentRepository;
import com.memory.commutator.Service.CDRService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
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
	private final CDRService cdrService;

	public CommutatorApplication(AbonentRepository abonentRepository, CDRService cdrService) {
		this.abonentRepository = abonentRepository;
		this.cdrService = cdrService;
	}

	@Override
	public void run(String... args) {
		System.out.println("Commutator started");
		abonentRepository.saveAll(abonents);
		cdrService.generateCDRRecordsAsync(100, 5);
	}

	public static void main(String[] args) {
		SpringApplication.run(CommutatorApplication.class, args);
	}
}

