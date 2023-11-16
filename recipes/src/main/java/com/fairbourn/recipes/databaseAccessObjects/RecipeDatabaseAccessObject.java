package com.fairbourn.recipes.databaseAccessObjects;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fairbourn.recipes.model.Recipe;

public interface RecipeDatabaseAccessObject {

	default boolean insertRecipe(Recipe recipe) {
		UUID id = UUID.randomUUID();
		return insertRecipe(id, recipe);
	};
	
	boolean insertRecipe(UUID id, Recipe recipe);

	List<Recipe> getAllRecipes();

	Optional<Recipe> getRecipeById(UUID id);

	boolean deleteRecipeById(UUID id);

	boolean updateRecipeById(UUID id, Recipe recipe);
}
