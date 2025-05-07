package com.memory.brt.Messaging;

import com.memory.brt.Model.CDRecord;
import com.memory.brt.Repository.CDRecordRepository;
import com.memory.brt.Util.CallType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CDRMessageListener implements CommandLineRunner {
    private final CDRecordRepository cdrRepository;



    public CDRMessageListener(CDRecordRepository cdrRepository) {
        this.cdrRepository = cdrRepository;
    }

    @RabbitListener(queues = "cdr.to.brt")
    public void receive(List<CDRecord> messages) {
        List<CDRecord> entities = messages.stream()
                .map(m -> {
                    CallType ct = CallType.fromCode(m.getCallType().getCode());
                    CDRecord cdr = new CDRecord(
                            ct,
                            m.getCaller(),
                            m.getReceiver(),
                            m.getStartTime(),
                            m.getEndTime()
                    );
                    return cdr;
                })
                .toList();
        System.out.println("Записи приняты!");
        cdrRepository.saveAll(entities);
    }

//    @RabbitListener(queues = "cdr.to.brt")
//    public void receiveRawMessage(org.springframework.amqp.core.Message message) {
//        String raw = new String(message.getBody(), StandardCharsets.UTF_8);
//        System.out.println("ПРИШЛО СООБЩЕНИЕ:");
//        System.out.println(raw);
//    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Версия 1.0, CDRMessageListener");
    }
}
