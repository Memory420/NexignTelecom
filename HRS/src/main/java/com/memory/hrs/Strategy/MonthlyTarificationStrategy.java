package com.memory.hrs.Strategy;

import com.memory.hrs.Models.Tariff;
import com.memory.hrs.Utils.CallInfo;
import com.memory.hrs.Utils.TarificationStrategy;
import org.springframework.stereotype.Component;

@Component
public class MonthlyTarificationStrategy implements TarificationStrategy {
    @Override
    public double calculate(CallInfo call, Tariff tariff) {
        System.out.println("MonthlyTarificationStrategy");
        return 0;
    }
}
