package com.memory.hrs.Models;

public class ChargeDTO {
    private final String phoneNumber;
    private final double chargeAmount;

    public ChargeDTO(String phoneNumber, double chargeAmount) {
        this.phoneNumber = phoneNumber;
        this.chargeAmount = chargeAmount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }
}
