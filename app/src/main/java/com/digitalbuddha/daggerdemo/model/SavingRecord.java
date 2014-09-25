package com.digitalbuddha.daggerdemo.model;

/**
 * Created by patriciaestridge on 9/24/14.
 */
//SavingsRecord Model
public class SavingRecord {

    private String id;
    private SavingsType savingsType;
    private String amount;
    private int multiplier;
    private String frequency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SavingsType getSavingsType() {
        return savingsType;
    }

    public void setSavingsType(SavingsType savingsType) {
        this.savingsType = savingsType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
