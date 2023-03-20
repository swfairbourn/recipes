package com.fairbourn.finance.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

	private String merchant;
	private double cost;
	
	
	public Transaction(@JsonProperty("id") UUID id, 
						@JsonProperty("merchant") String merchant, 
						@JsonProperty("cost") double cost) {
		this.merchant = merchant;
		this.cost = cost;
	}


	public String getMerchant() {
		return merchant;
	}

	public double getCost() {
		return cost;
	}
	
}
