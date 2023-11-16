package com.fairbourn.finance.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Income {
	private int id;
    private String source;
    private LocalDate date;
    private double amount;

    public Income(@JsonProperty("incomeId") int id,
    		@JsonProperty("source") String source,
    		@JsonProperty("date") LocalDate date,
    		@JsonProperty("amount")double amount) {
    	this.id = id;
    	this.source = source;
    	this.date = date;
    	this.amount = amount;
    }

	public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}


