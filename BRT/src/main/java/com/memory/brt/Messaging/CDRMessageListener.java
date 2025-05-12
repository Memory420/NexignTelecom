package com.memory.brt.Messaging;

import com.memory.brt.Model.CDRecord;
import com.memory.brt.Model.TarificationRequest;
import com.memory.brt.Repository.CDRecordRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class CDRMessageListener implements CommandLineRunner {
    private final CDRecordRepository cdrRepository;
    private final RabbitTemplate rabbit;

    public CDRMessageListener(CDRecordRepository cdrRepository, RabbitTemplate rabbitTemplate) {
        this.cdrRepository = cdrRepository;
        this.rabbit = rabbitTemplate;
    }

    @RabbitListener(queues = "cdr.to.brt")
    public void receive(List<CDRecord> messages) {
        System.out.println("Записи приняты!");
        List<TarificationRequest> tarificationRequests = messages.stream()
                .map(cdr -> new TarificationRequest(
                        cdr.getCaller(),
                        cdr.getReceiver(),
                        Duration.between(cdr.getStartTime(), cdr.getEndTime()).getSeconds()
                )).toList();
        rabbit.convertAndSend("brt.to.hrs", tarificationRequests);
        cdrRepository.saveAll(messages);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Версия 1.1, CDRMessageListener");
    }
}
