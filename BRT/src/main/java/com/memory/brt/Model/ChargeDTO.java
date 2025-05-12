package com.memory.brt.Model;

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

    @Override
    public String toString() {
        return "ChargeDTO{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", chargeAmount=" + chargeAmount +
                '}';
    }
}
