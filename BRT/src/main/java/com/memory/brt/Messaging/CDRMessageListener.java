package com.memory.brt.Messaging;

import com.memory.brt.Model.CDRecord;
import com.memory.brt.Repository.CDRecordRepository;
import com.memory.brt.Util.CallType;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CDRMessageListener {
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
                    System.out.println(cdr.toString());
                    return cdr;
                })
                .toList();
        cdrRepository.saveAll(entities);
    }
}
