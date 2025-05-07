package com.memory.hrs.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff", nullable = false)
    private Tariff tariff;

    @Column(name = "minutes_left", nullable = false)
    private long minutesLeft;

    @Column(name = "require_monthly_fee_pay", nullable = false)
    private boolean requireMonthlyFeePay;

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart = LocalDate.now();

    public Subscriber(String number, Tariff tariff, long minutesLeft, boolean requireMonthlyFeePay, LocalDate periodStart) {
        this.number = number;
        this.tariff = tariff;
        this.minutesLeft = minutesLeft;
        this.requireMonthlyFeePay = requireMonthlyFeePay;
        this.periodStart = periodStart;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", tariff=" + tariff +
                ", minutesLeft=" + minutesLeft +
                ", requireMonthlyFeePay=" + requireMonthlyFeePay +
                ", periodStart=" + periodStart +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public long getMinutesLeft() {
        return minutesLeft;
    }

    public boolean isRequireMonthlyFeePay() {
        return requireMonthlyFeePay;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }
}
