package com.memory.brt;

import com.memory.brt.Model.Abonent;
import com.memory.brt.Repository.AbonentRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class BrtApplication implements CommandLineRunner {
    @Autowired
    private AbonentRepository abonentRepository;

    public static void main(String[] args) {
        SpringApplication.run(BrtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Abonent> abonents = List.of(
                createAbonent("79990111111", "Robert Downey Jr."),
                createAbonent("79990222222", "Scarlett Johansson"),
                createAbonent("79990333333", "Chris Evans"),
                createAbonent("79990444444", "Chris Hemsworth"),
                createAbonent("79990555555", "Tom Holland"),
                createAbonent("79990666666", "Zendaya"),
                createAbonent("79990777777", "Mark Ruffalo")
        );

        abonentRepository.saveAll(abonents);
        System.out.println("Абоненты сохранены");
    }

    private Abonent createAbonent(String number, String name) {
        Abonent a = new Abonent();
        a.setNumber(number);
        a.setFullName(name);
        a.setBalance((float) ((Math.random() * 100 + 50) * 100) / 100);
        a.setRegistrationDate(LocalDateTime.now().minusDays((long) (Math.random() * 365)));
        a.setTariffId(11L);
        return a;
    }

    @Bean
    public Queue cdrQueue() {
        return new Queue("cdr.to.brt", true);
    }
}
