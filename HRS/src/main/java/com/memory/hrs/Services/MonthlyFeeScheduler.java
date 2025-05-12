package com.memory.hrs.Services;

import com.memory.hrs.Models.ChargeDTO;
import com.memory.hrs.Models.Subscriber;
import com.memory.hrs.Repository.SubscriberRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MonthlyFeeScheduler {

    private final SubscriberRepository subscriberRepository;
    private final RabbitTemplate rabbit;

    public MonthlyFeeScheduler(SubscriberRepository subscriberRepository, RabbitTemplate rabbit) {
        this.subscriberRepository = subscriberRepository;
        this.rabbit = rabbit;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void chargeMonthlyFees() {
        LocalDate today = LocalDate.now();
        List<Subscriber> toCharge = subscriberRepository.findAllByRequireMonthlyFeePayTrue();

        for (Subscriber s : toCharge) {
            if (s.getPeriodStart().plusDays(30).isAfter(today)) {
                continue;
            }

            double fee = s.getTariff().getMonthlyFee();
            rabbit.convertAndSend("", "hrs.to.brt", new ChargeDTO(s.getNumber(), fee));

            s.setRequireMonthlyFeePay(false);
            s.setMinutesLeft(s.getTariff().getIncludedMinutes());
            s.setPeriodStart(today);
            subscriberRepository.save(s);
        }
    }
}

