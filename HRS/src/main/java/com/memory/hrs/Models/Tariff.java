package com.memory.hrs.Models;

import com.memory.hrs.Utils.TariffType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Tariff {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private TariffType type;

    private double priceToRomashka; // сколько стоим минута, когда звонишь абоненту ромашки
    private double priceToOthers; // сколько стоим минута, когда звонишь чужому абоненту

    private int includedMinutes;
    private double monthlyFee;

    public Tariff(Long id, TariffType type, double priceToRomashka, double priceToOthers, int includedMinutes, double monthlyFee) {
        this.id = id;
        this.type = type;
        this.priceToRomashka = priceToRomashka;
        this.priceToOthers = priceToOthers;
        this.includedMinutes = includedMinutes;
        this.monthlyFee = monthlyFee;
    }

    public Tariff() {
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", type=" + type +
                ", priceToRomashka=" + priceToRomashka +
                ", priceToOthers=" + priceToOthers +
                ", includedMinutes=" + includedMinutes +
                ", monthlyFee=" + monthlyFee +
                '}';
    }

    public Long getId() {
        return id;
    }

    public TariffType getType() {
        return type;
    }

    public double getPriceToRomashka() {
        return priceToRomashka;
    }

    public double getPriceToOthers() {
        return priceToOthers;
    }

    public int getIncludedMinutes() {
        return includedMinutes;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }
}
