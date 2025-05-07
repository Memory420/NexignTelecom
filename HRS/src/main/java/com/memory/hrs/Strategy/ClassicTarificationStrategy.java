package com.memory.hrs.Strategy;

import com.memory.hrs.Models.Tariff;
import com.memory.hrs.Utils.CallInfo;
import com.memory.hrs.Utils.CallType;
import com.memory.hrs.Utils.TarificationStrategy;
import org.springframework.stereotype.Component;

@Component
public class ClassicTarificationStrategy implements TarificationStrategy {
    @Override
    public double calculate(CallInfo call, Tariff tariff) {
        System.out.println("ClassicTarificationStrategy");
        if (call.getCallType() == CallType.INCOMING) {
            
        }
        return 0;
    }
}
