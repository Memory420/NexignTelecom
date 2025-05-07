package com.memory.hrs;

import com.memory.hrs.Models.Subscriber;
import com.memory.hrs.Models.Tariff;
import com.memory.hrs.Repository.SubscriberRepository;
import com.memory.hrs.Repository.TariffRepository;
import com.memory.hrs.Utils.TariffType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HrsApplication implements CommandLineRunner {
    public static List<Subscriber> INITIAL_SUBSCRIBERS(Tariff monthlyTariff, Tariff classicTariff) {
        return List.of(
                new Subscriber("79990111111", monthlyTariff, 0L, false, LocalDate.now()),
                new Subscriber("79990222222", monthlyTariff, 0L, false, LocalDate.now()),
                new Subscriber("79990333333", monthlyTariff, 0L, false, LocalDate.now()),
                new Subscriber("79990444444", classicTariff, 0L, false, LocalDate.now()),
                new Subscriber("79990555555", classicTariff, 0L, false, LocalDate.now()),
                new Subscriber("79990666666", classicTariff, 0L, false, LocalDate.now()),
                new Subscriber("79990777777", classicTariff, 0L, false, LocalDate.now())
        );
    }

    private final TariffRepository tariffRepository;
    private final SubscriberRepository subscriberRepository;

    public HrsApplication(TariffRepository tariffRepository, SubscriberRepository subscriberRepository) {
        this.tariffRepository = tariffRepository;
        this.subscriberRepository = subscriberRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(HrsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Tariff classic = new Tariff(11L,
                TariffType.CLASSIC,
                1.5,
                2.5,
                0,
                0);

        Tariff monthly = new Tariff(12L,
                TariffType.MONTHLY,
                1.5,
                2.5,
                50,
                100);
        tariffRepository.save(classic);
        tariffRepository.save(monthly);
        Tariff classicFromDb = tariffRepository.findTariffByType(TariffType.CLASSIC);
        Tariff monthlyFromDb = tariffRepository.findTariffByType(TariffType.MONTHLY);
        subscriberRepository.saveAll(INITIAL_SUBSCRIBERS(classic, monthlyFromDb));
        System.out.println(classicFromDb.toString());
    }

}
