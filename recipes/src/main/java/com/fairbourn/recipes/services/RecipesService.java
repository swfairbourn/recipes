package com.fairbourn.recipes.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fairbourn.recipes.databaseAccessObjects.RecipeDatabaseAccessObject;
import com.fairbourn.recipes.model.Ingredient;
import com.fairbourn.recipes.model.Recipe;
import com.fairbourn.recipes.model.RecipeCriteria;
import com.fairbourn.recipes.model.UnitOfMeasurment;

@Service
public class RecipesService {

	private final RecipeDatabaseAccessObject databaseAccessObject;
	
	@Autowired
	public RecipesService(@Qualifier("inMemory") RecipeDatabaseAccessObject databaseAccessObject) {
		this.databaseAccessObject = databaseAccessObject;
	}
	
	public List<Recipe> getAllRecipes() {
		return databaseAccessObject.getAllRecipes();
	}
	
	public boolean insertRecipe(Recipe recipe) {
		//TODO: Add the tags from the recipe to the tags database if they aren't already there
		//TODO: Add the nationality to the nationality database if it isn't already there
		return databaseAccessObject.insertRecipe(recipe);
	}

	public Optional<Recipe> getRecipeById(UUID id) {
		return databaseAccessObject.getRecipeById(id);
	}

	public boolean deleteRecipeById(UUID id) {
		//TODO: Remove any tags from the tags database that no longer have any recipes with that tag
		//TODO: Remove any nationalities from the nationality database that no longer have any recipes with that nationality
		return databaseAccessObject.deleteRecipeById(id);
	}

	public boolean updateRecipeById(UUID id, Recipe recipe) {
		//TODO: Check if new tags or nationalities need to be added or removed from the tags and nationality databases
		return databaseAccessObject.updateRecipeById(id, recipe);
	}
	
	public List<Recipe> getAllRecipesMatchingCriteria(RecipeCriteria recipeCriteria) {
		//TODO: Get all recipes that match the criteria
		List<Ingredient> ingredients = new ArrayList<>(Arrays.asList(new Ingredient("testIngredient", 10, UnitOfMeasurment.DASH)));
		List<String> tags = new ArrayList<>(Arrays.asList("Grill", "Dinner"));
		Recipe recipe = new Recipe(UUID.randomUUID(), "titleTest", 5, ingredients, "These are the directions", "French", tags);
		return new ArrayList<>(Arrays.asList(recipe));
	}
}
