package com.memory.hrs.Strategy;

import com.memory.hrs.Models.Subscriber;
import com.memory.hrs.Models.Tariff;
import com.memory.hrs.Repository.SubscriberRepository;
import com.memory.hrs.Utils.CallInfo;
import com.memory.hrs.Utils.TarificationStrategy;
import org.springframework.stereotype.Component;

@Component
public class MonthlyTarificationStrategy implements TarificationStrategy {
    private final SubscriberRepository subscriberRepository;
    private final ClassicTarificationStrategy classicStrategy;

    public MonthlyTarificationStrategy(SubscriberRepository subscriberRepository, ClassicTarificationStrategy classicStrategy) {
        this.subscriberRepository = subscriberRepository;
        this.classicStrategy = classicStrategy;
    }

    @Override
    public double calculate(CallInfo call, Tariff tariff) {
        System.out.println("MonthlyTarificationStrategy");

        Subscriber sub = subscriberRepository.findByNumber(call.getPhoneNumber());
        long leftMinutes = sub != null ? sub.getMinutesLeft() : 0;
        long durationMin = (long) Math.ceil(call.getDuration() / 60.0);

        if (leftMinutes >= durationMin) {
            return 0.0;
        } else if (leftMinutes <= 0) {
            return classicStrategy.calculate(call, tariff);
        } else {
            long paidMinutes = durationMin - leftMinutes;

            CallInfo paidPart = new CallInfo(
                    call.getPhoneNumber(),
                    call.getCallType(),
                    paidMinutes * 60,
                    call.getTargetNumber()
            );

            return classicStrategy.calculate(paidPart, tariff);
        }
    }
}
