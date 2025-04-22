package com.memory.commutator.Model;

import com.memory.commutator.Util.Operator;

public class Abonent {
    private String number;
    private Operator operator;

    public Abonent() {
    }

    public Abonent(String number, Operator operator) {
        this.number = number;
        this.operator = operator;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
