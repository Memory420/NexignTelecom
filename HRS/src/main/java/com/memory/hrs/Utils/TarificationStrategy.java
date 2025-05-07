package com.memory.hrs.Utils;

import com.memory.hrs.Models.Tariff;

public interface TarificationStrategy {
    double calculate(CallInfo call, Tariff tariff);
}
