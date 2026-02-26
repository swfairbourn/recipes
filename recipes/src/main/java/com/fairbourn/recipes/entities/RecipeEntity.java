package com.fairbourn.recipes.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * JPA entity for the recipes table.
 * Kept separate from the Recipe POJO so the service/API layer is unchanged.
 */
@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    @Column(name = "recipe_id", nullable = false, updatable = false)
    private String recipeId; // stored as String since SQLite has no native UUID type

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "rating")
    private int rating;

    @Column(name = "directions", columnDefinition = "TEXT")
    private String directions;

    @Column(name = "nationality")
    private String nationality;

    @OneToMany(
        mappedBy = "recipe",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @OrderBy("sortIndex ASC")
    private List<IngredientEntity> ingredients = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "recipe_tags", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    public RecipeEntity() {}

    public RecipeEntity(String recipeId, String title, int rating, String directions, String nationality) {
        this.recipeId = recipeId;
        this.title = title;
        this.rating = rating;
        this.directions = directions;
        this.nationality = nationality;
    }

    // --- Getters & Setters ---

    public String getRecipeId() { return recipeId; }
    public void setRecipeId(String recipeId) { this.recipeId = recipeId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getDirections() { return directions; }
    public void setDirections(String directions) { this.directions = directions; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public List<IngredientEntity> getIngredients() { return ingredients; }
    public void setIngredients(List<IngredientEntity> ingredients) { this.ingredients = ingredients; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
