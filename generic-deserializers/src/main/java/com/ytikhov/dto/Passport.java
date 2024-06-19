package com.ytikhov.dto;

public class Passport {
    private String number;

    public Passport() {
    }

    public Passport(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
