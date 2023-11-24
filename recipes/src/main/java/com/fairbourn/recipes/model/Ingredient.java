package com.fairbourn.recipes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {

	private double amount;
	private UnitOfMeasurement unitOfMeasurement;
	private String ingredient;
	
	public Ingredient(
			@JsonProperty("value") double amount,
			@JsonProperty("unitOfMeasurement") UnitOfMeasurement unitOfMeasurement,
			@JsonProperty("ingredient") String ingredient
	) {
		this.amount = amount;
		this.unitOfMeasurement = unitOfMeasurement;
		this.ingredient = ingredient;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public UnitOfMeasurement getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	
	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	
}
