package com.fairbourn.finance.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

	private final UUID transactionId;
	private String merchant;
	private double cost;
	
	
	public Transaction(@JsonProperty("transactionId") UUID transactionId, 
					   @JsonProperty("merchant") String merchant, 
					   @JsonProperty("cost") double cost) {
		this.transactionId = transactionId;
		this.merchant = merchant;
		this.cost = cost;
	}

	public UUID getTransactionId() {
		return transactionId;
	}

	public String getMerchant() {
		return merchant;
	}

	public double getCost() {
		return cost;
	}
	
}
