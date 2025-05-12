package com.memory.hrs.Strategy;

import com.memory.hrs.Models.Tariff;
import com.memory.hrs.Repository.SubscriberRepository;
import com.memory.hrs.Utils.CallInfo;
import com.memory.hrs.Utils.CallType;
import com.memory.hrs.Utils.TarificationStrategy;
import org.springframework.stereotype.Component;

@Component
public class ClassicTarificationStrategy implements TarificationStrategy {
    private final SubscriberRepository subscriberRepository;

    public ClassicTarificationStrategy(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Override
    public double calculate(CallInfo call, Tariff tariff) {
        System.out.println("ClassicTarificationStrategy");

        if (call.getCallType() == CallType.OUTGOING) {
            return 0.0;
        }

        boolean isRomashka = isRomashkaNumber(call.getTargetNumber());

        double ratePerMinute = isRomashka
                ? tariff.getPriceToRomashka()
                : tariff.getPriceToOthers();

        long durationInMinutes = (long) Math.ceil(call.getDuration() / 60.0);

        return ratePerMinute * durationInMinutes;
    }

    private boolean isRomashkaNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        return subscriberRepository.findByNumber(phoneNumber) != null;
    }
}
