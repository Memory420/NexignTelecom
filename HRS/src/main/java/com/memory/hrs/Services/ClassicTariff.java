package com.memory.hrs.Services;

import com.memory.hrs.Models.Tariff;
import com.memory.hrs.Utils.CallInfo;
import com.memory.hrs.Utils.TarificationStrategy;


public class ClassicTariff implements TarificationStrategy {
    @Override
    public double calculate(CallInfo call, Tariff tariff) {
        return 0.0;
    }
}
