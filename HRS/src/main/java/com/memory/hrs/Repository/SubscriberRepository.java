package com.memory.hrs.Repository;

import com.memory.hrs.Models.Subscriber;
import com.memory.hrs.Models.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Subscriber findByNumber(String number);
    @Query("SELECT s.tariff FROM Subscriber s WHERE s.number = :number")
    Tariff findTariffByNumber(@Param("number") String number);
    List<Subscriber> findAllByRequireMonthlyFeePayTrue();
}
