package com.fairbourn.recipes.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecipeCriteria {

    private List<Rating> ratings;
    private List<Nationality> nationalities;
    private List<Tag> tags;
    
    public RecipeCriteria() {}
	
	public RecipeCriteria(
			@JsonProperty("ratings") List<Rating> ratings,
			@JsonProperty("nationalities") List<Nationality> nationalities,
			@JsonProperty("tags") List<Tag> tags
	) {
		this.ratings = ratings;
		this.nationalities = nationalities;
		this.tags = tags;
	}

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Nationality> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<Nationality> nationalities) {
        this.nationalities = nationalities;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

	public String toStringtest() {
		StringBuilder sb = new StringBuilder();
		for (Rating rating : ratings) {
			sb.append(" Rating: " + rating.getValue());
		}
		for (Nationality n : nationalities) {
			sb.append(" Nationality: " + n.getValue());
		}
		for (Tag t : tags) {
			sb.append(" Tag: " + t.getValue());
		}
		return sb.toString();
	}
	
}
