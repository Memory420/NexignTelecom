package com.memory.hrs.Services;

import com.memory.hrs.Models.ChargeDTO;
import com.memory.hrs.Repository.SubscriberRepository;
import com.memory.hrs.Strategy.TarificationStrategyFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class ChargeService implements CommandLineRunner {

    private final TarificationStrategyFactory strategyFactory;
    private final SubscriberRepository subscriberRepository;
    private final RabbitTemplate rabbit;

    public ChargeService(TarificationStrategyFactory strategyFactory,
                         SubscriberRepository subscriberRepository,
                         RabbitTemplate rabbitTemplate) {
        this.strategyFactory = strategyFactory;
        this.subscriberRepository = subscriberRepository;
        this.rabbit = rabbitTemplate;
    }


    @Override
    public void run(String... args) throws Exception {
        ChargeDTO dto = new ChargeDTO(
                "79990444444",
                50
                );
        rabbit.convertAndSend("", "hrs.to.brt", dto);
    }
}
