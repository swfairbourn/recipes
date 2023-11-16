package com.fairbourn.recipes.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fairbourn.recipes.databaseAccessObjects.RecipeDatabaseAccessObject;
import com.fairbourn.recipes.model.Recipe;

public class RecipesService {

	private final RecipeDatabaseAccessObject databaseAccessObject;
	
	@Autowired
	public RecipesService(@Qualifier("postgres") RecipeDatabaseAccessObject databaseAccessObject) {
		this.databaseAccessObject = databaseAccessObject;
	}
	
	public List<Recipe> getAllRecipes() {
		return databaseAccessObject.getAllRecipes();
	}
	
	public boolean insertRecipe(Recipe recipe) {
		return databaseAccessObject.insertRecipe(recipe);
	}

	public Optional<Recipe> getRecipeById(UUID id) {
		return databaseAccessObject.getRecipeById(id);
	}

	public boolean deleteRecipeById(UUID id) {
		return databaseAccessObject.deleteRecipeById(id);
	}

	public boolean updateRecipeById(UUID id, Recipe recipe) {
		return databaseAccessObject.updateRecipeById(id, recipe);
	}
}
