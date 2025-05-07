package com.memory.hrs.Strategy;

import com.memory.hrs.Utils.TariffType;
import com.memory.hrs.Utils.TarificationStrategy;
import org.springframework.stereotype.Component;

@Component
public class TarificationStrategyFactory {
    private final ClassicTarificationStrategy classic;
    private final MonthlyTarificationStrategy monthly;

    public TarificationStrategyFactory(ClassicTarificationStrategy classic, MonthlyTarificationStrategy monthly) {
        this.classic = classic;
        this.monthly = monthly;
    }
    public TarificationStrategy resolve(TariffType type) {
        return switch (type) {
            case CLASSIC -> classic;
            case MONTHLY -> monthly;
        };
    }
}
