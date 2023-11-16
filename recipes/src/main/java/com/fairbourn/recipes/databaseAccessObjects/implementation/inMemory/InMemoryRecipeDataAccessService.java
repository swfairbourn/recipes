package com.fairbourn.recipes.databaseAccessObjects.implementation.inMemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.fairbourn.recipes.databaseAccessObjects.RecipeDatabaseAccessObject;
import com.fairbourn.recipes.model.Recipe;

@Repository("inMemory")
public class InMemoryRecipeDataAccessService implements RecipeDatabaseAccessObject {

	private static List<Recipe> DB = new ArrayList<>();
	
	@Override
	public boolean insertRecipe(UUID id, Recipe recipe) {
		DB.add(new Recipe(id, 
				recipe.getTitle(),
				recipe.getRating(),
				recipe.getInstructions(),
				recipe.getDirections(),
				recipe.getNationality(),
				recipe.getTags()));
		return true;
	}

	@Override
	public List<Recipe> getAllRecipes() {
		return DB;
	}

	@Override
	public Optional<Recipe> getRecipeById(UUID id) {
		return DB.stream()
		.filter(transaction -> transaction.getRecipeId().equals(id))
		.findFirst();
	}

	@Override
	public boolean deleteRecipeById(UUID id) {
		DB.stream().filter(recipe -> recipe.getRecipeId().equals(id));
		Optional<Recipe> recipeMaybe = getRecipeById(id);
		if (recipeMaybe.isEmpty()) {
			return false;
		} else {
			DB.remove(recipeMaybe.get());
			return true;
		}
	}

	@Override
	public boolean updateRecipeById(UUID id, Recipe recipe) {
		return getRecipeById(id)
				.map(t -> {
					int indexOfTransactionToDelete = DB.indexOf(t);
					if (indexOfTransactionToDelete >= 0) {
						DB.set(indexOfTransactionToDelete, new Recipe(id, 
								recipe.getTitle(),
								recipe.getRating(),
								recipe.getInstructions(), 
								recipe.getDirections(), 
								recipe.getNationality(),
								recipe.getTags()));
						return true;
					}
					return false;
				})
				.orElse(false);
	}
}
