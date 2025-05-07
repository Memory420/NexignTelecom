package com.memory.hrs.Repository;

import com.memory.hrs.Models.Tariff;
import com.memory.hrs.Utils.TariffType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Tariff findTariffByType(TariffType type);
}
