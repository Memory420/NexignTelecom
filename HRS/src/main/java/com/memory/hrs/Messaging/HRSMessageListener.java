package com.memory.hrs.Messaging;

import com.memory.hrs.Models.ChargeDTO;
import com.memory.hrs.Models.Subscriber;
import com.memory.hrs.Models.Tariff;
import com.memory.hrs.Models.TarificationRequest;
import com.memory.hrs.Repository.SubscriberRepository;
import com.memory.hrs.Strategy.TarificationStrategyFactory;
import com.memory.hrs.Utils.CallInfo;
import com.memory.hrs.Utils.CallType;
import com.memory.hrs.Utils.TariffType;
import com.memory.hrs.Utils.TarificationStrategy;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class HRSMessageListener {
    private final SubscriberRepository subscriberRepository;
    private final TarificationStrategyFactory strategyFactory;
    private final RabbitTemplate rabbit;

    public HRSMessageListener(SubscriberRepository subscriberRepository, TarificationStrategyFactory tarificationStrategyFactory, RabbitTemplate rabbit) {
        this.subscriberRepository = subscriberRepository;
        this.strategyFactory = tarificationStrategyFactory;
        this.rabbit = rabbit;
    }

    @Transactional
    @RabbitListener(queues = "brt.to.hrs")
    public void receive(List<TarificationRequest> requests) {
        for (TarificationRequest req : requests) {
            Subscriber caller = subscriberRepository.findByNumber(req.getNumber());
            if (caller == null) {
                continue;
            }

            long durationSec = req.getCallSeconds();
            long durationMin = (long) Math.ceil(durationSec / 60.0);

            if (caller.getTariff().getType() == TariffType.MONTHLY && caller.getMinutesLeft() > 0) {
                long deducted = Math.min(durationMin, caller.getMinutesLeft());
                caller.setMinutesLeft(caller.getMinutesLeft() - deducted);
                subscriberRepository.save(caller);
            }

            CallInfo callInfo = new CallInfo(
                    caller.getNumber(),
                    CallType.OUTGOING,
                    durationSec,
                    req.getTargetNumber()
            );
            handleCall(callInfo);
        }
    }

    public void handleCall(CallInfo call) {
        Tariff tariff = subscriberRepository.findTariffByNumber(call.getPhoneNumber());
        TarificationStrategy strategy = strategyFactory.resolve(tariff.getType());
        double charge = strategy.calculate(call, tariff);

        ChargeDTO dto = new ChargeDTO(call.getPhoneNumber(), charge);
        rabbit.convertAndSend("", "hrs.to.brt", dto);
    }
}
