package com.memory.brt;

import com.memory.brt.Model.Abonent;
import com.memory.brt.Repository.AbonentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@Order(1)
public class BrtApplication implements CommandLineRunner {
    @Autowired
    private AbonentRepository abonentRepository;

    public static void main(String[] args) {
        SpringApplication.run(BrtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception { // brt
        List<Abonent> abonents = List.of(
                createAbonent("79990111111", "Robert Downey Jr.", 11L),
                createAbonent("79990222222", "Scarlett Johansson", 11L),
                createAbonent("79990333333", "Chris Evans", 11L),
                createAbonent("79990444444", "Chris Hemsworth", 12L),
                createAbonent("79990555555", "Tom Holland", 12L),
                createAbonent("79990666666", "Zendaya", 12L),
                createAbonent("79990777777", "Mark Ruffalo", 12L)
        );

        abonentRepository.saveAll(abonents);
        System.out.println("Абоненты сохранены");
        System.out.println("Версия 1.1.2, BrtApplication");
    }

    private Abonent createAbonent(String number, String name, Long tariffId) {
        Abonent a = new Abonent();
        a.setNumber(number);
        a.setFullName(name);
        a.setBalance(100L);
//        a.setBalance((float) (Math.round((Math.random() * 100 + 50) * 10.0) / 10.0));
        a.setRegistrationDate(LocalDateTime.now().minusDays((long) (Math.random() * 365)));
        a.setTariffId(tariffId);
        return a;
    }
}
