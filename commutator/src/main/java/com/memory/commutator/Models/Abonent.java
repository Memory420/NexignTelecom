package com.memory.commutator.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Abonent {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;

    public Abonent(String number) {
        this.number = number;
    }

    public Abonent() {
    }

    public String getNumber() {
        return number;
    }

    public Long getId() {
        return id;
    }
}