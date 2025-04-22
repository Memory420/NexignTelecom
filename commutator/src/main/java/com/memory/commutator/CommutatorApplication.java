package com.memory.commutator;

import com.memory.commutator.Model.Abonent;
import com.memory.commutator.Service.CallSimulation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CommutatorApplication implements CommandLineRunner {

	@Autowired
	private RabbitTemplate rabbit;

	public static void main(String[] args) {
		SpringApplication.run(CommutatorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Abonent> abonents = new ArrayList<>(CallSimulation.ABONENTS);
		CallSimulation callSimulation = new CallSimulation(rabbit);
		callSimulation.runSimulation(abonents, 5, 7);
	}
}


