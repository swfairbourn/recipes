package com.fairbourn.finance.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Transaction {

    private final UUID transactionId;
    private String merchant;
    private double amount;
    private String category;
    private LocalDate transactionDate;

    public Transaction(
            @JsonProperty("transactionId") UUID transactionId,
            @JsonProperty("merchant") String merchant,
            @JsonProperty("amount") double amount,
            @JsonProperty("category") String category,
            @JsonProperty("transactionDate") LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.merchant = merchant;
        this.amount = amount;
        this.category = category;
        this.transactionDate = transactionDate;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public String getMerchant() {
        return merchant;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}


