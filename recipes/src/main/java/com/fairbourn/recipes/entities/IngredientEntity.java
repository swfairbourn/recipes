package com.fairbourn.recipes.entities;

import jakarta.persistence.*;

/**
 * JPA entity for the ingredients table.
 * sortIndex preserves the ingredient order from the original list.
 */
@Entity
@Table(name = "ingredients")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "unit_of_measurement", nullable = true)
    private String unitOfMeasurement; // stored as enum name string

    @Column(name = "ingredient", nullable = false)
    private String ingredient;

    @Column(name = "sort_index", nullable = false)
    private int sortIndex;

    public IngredientEntity() {}

    public IngredientEntity(RecipeEntity recipe, double amount, String unitOfMeasurement, String ingredient, int sortIndex) {
        this.recipe = recipe;
        this.amount = amount;
        this.unitOfMeasurement = unitOfMeasurement;
        this.ingredient = ingredient;
        this.sortIndex = sortIndex;
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }

    public RecipeEntity getRecipe() { return recipe; }
    public void setRecipe(RecipeEntity recipe) { this.recipe = recipe; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getUnitOfMeasurement() { return unitOfMeasurement; }
    public void setUnitOfMeasurement(String unitOfMeasurement) { this.unitOfMeasurement = unitOfMeasurement; }

    public String getIngredient() { return ingredient; }
    public void setIngredient(String ingredient) { this.ingredient = ingredient; }

    public int getSortIndex() { return sortIndex; }
    public void setSortIndex(int sortIndex) { this.sortIndex = sortIndex; }
}