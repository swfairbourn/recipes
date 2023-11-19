package com.fairbourn.recipes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {

	private String name;
	private double value;
	private UnitOfMeasurment unitOfMeasurement;
	
	public Ingredient(
			@JsonProperty("name") String name,
			@JsonProperty("value") double value,
			@JsonProperty("unitOfMeasurement") UnitOfMeasurment unitOfMeasurement
	) {
		this.name = name;
		this.value = value;
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public UnitOfMeasurment getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(UnitOfMeasurment unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}
	
}
