package com.memory.brt.Messaging;

import com.memory.brt.Model.Abonent;
import com.memory.brt.Model.ChargeDTO;
import com.memory.brt.Repository.AbonentRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(2)
@Component
public class HRSMessageListener implements CommandLineRunner {

    private final AbonentRepository abonentRepository;

    public HRSMessageListener(AbonentRepository abonentRepository) {
        this.abonentRepository = abonentRepository;
    }

    @RabbitListener(queues = "hrs.to.brt")
    public void receive(ChargeDTO dto) {
        String number = dto.getPhoneNumber();
        double charge = dto.getChargeAmount();

        Abonent abonent = abonentRepository.findByNumber(number);
        if (abonent == null) {
            return;
        }

        float newBalance = abonent.getBalance() - (float) charge;
        abonent.setBalance(newBalance);
        abonentRepository.save(abonent);

        if ("79990444444".equals(number)) {
            System.out.println("Должно быть вторым: " + abonent.getBalance());
        }
    }


    @Override
    public void run(String... args) {
        Abonent abonent = abonentRepository.findByNumber("79990444444");
        System.out.println("Должно быть первым: " + abonent.getBalance());
        System.out.println("HRSMessageListener версия 1.2");
    }
}
