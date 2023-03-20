package com.fairbourn.finance.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

	private final UUID id;
	private String merchant;
	private double cost;
	
	
	public Transaction(@JsonProperty("id") UUID id, 
					   @JsonProperty("merchant") String merchant, 
					   @JsonProperty("cost") double cost) {
		this.id = id;
		this.merchant = merchant;
		this.cost = cost;
	}

	public UUID getId() {
		return id;
	}

	public String getMerchant() {
		return merchant;
	}

	public double getCost() {
		return cost;
	}
	
}
