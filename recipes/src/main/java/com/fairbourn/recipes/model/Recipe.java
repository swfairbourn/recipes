package com.fairbourn.recipes.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {
	
	private UUID recipeId;
	private String title;
	private int rating;
	private List<String> instructions;
	private String directions;
	private String nationality;
	private List<String> tags;
	
	public Recipe(
			@JsonProperty("recipeId") UUID recipeId,
			@JsonProperty("title") String title,
			@JsonProperty("rating") int rating,
			@JsonProperty("instructions") List<String> instructions,
			@JsonProperty("directions") String directions,
			@JsonProperty("nationality") String nationality,
			@JsonProperty("tags") List<String> tags
	) {
		this.recipeId = recipeId;
		this.title = title;
		this.rating = rating;
		this.instructions = instructions;
		this.directions = directions;
		this.nationality = nationality;
		this.tags = tags;
	}
	
	public UUID getRecipeId() {
		return recipeId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public List<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
