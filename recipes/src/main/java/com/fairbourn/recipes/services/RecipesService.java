package com.fairbourn.recipes.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fairbourn.recipes.databaseAccessObjects.NationalitiesDatabaseAccessObject;
import com.fairbourn.recipes.databaseAccessObjects.RecipeDatabaseAccessObject;
import com.fairbourn.recipes.databaseAccessObjects.SqliteNationalitiesDatabaseAccessObject;
import com.fairbourn.recipes.databaseAccessObjects.SqliteTagsDatabaseAccessObject;
import com.fairbourn.recipes.databaseAccessObjects.TagsDatabaseAccessObject;
import com.fairbourn.recipes.model.Recipe;

@Service
public class RecipesService {

	private final RecipeDatabaseAccessObject databaseAccessObject;
	private final TagsDatabaseAccessObject tagsAccessObject;
	private final NationalitiesDatabaseAccessObject nationalitiesAccessObject;

	@Autowired
	public RecipesService(
			@Qualifier("inMemory") RecipeDatabaseAccessObject databaseAccessObject,
			@Qualifier("inMemoryTags") TagsDatabaseAccessObject tagsAccessObject,
			@Qualifier("inMemoryNationalities") NationalitiesDatabaseAccessObject nationalitiesAccessObject
	) {
		this.databaseAccessObject = databaseAccessObject;
		this.tagsAccessObject = tagsAccessObject;
		this.nationalitiesAccessObject = nationalitiesAccessObject;
	}

	public List<Recipe> getAllRecipes() {
		List<Recipe> recipes = databaseAccessObject.getAllRecipes();
		recipes.sort(Comparator.comparing(Recipe::getTitle));
		return recipes;
	}

	public boolean insertRecipe(Recipe recipe) {
		boolean inserted = databaseAccessObject.insertRecipe(recipe);
		if (inserted) {
			syncTagsOnInsert(recipe);
			syncNationalityOnInsert(recipe);
		}
		return inserted;
	}

	public Optional<Recipe> getRecipeById(UUID id) {
		return databaseAccessObject.getRecipeById(id);
	}

	public boolean deleteRecipeById(UUID id) {
		// Capture the recipe before deleting so we can check what to clean up
		Optional<Recipe> existing = databaseAccessObject.getRecipeById(id);
		boolean deleted = databaseAccessObject.deleteRecipeById(id);
		if (deleted && existing.isPresent()) {
			syncTagsOnDelete(existing.get());
			syncNationalityOnDelete(existing.get());
		}
		return deleted;
	}

	public boolean updateRecipeById(UUID id, Recipe recipe) {
		Optional<Recipe> existing = databaseAccessObject.getRecipeById(id);
		boolean updated = databaseAccessObject.updateRecipeById(id, recipe);
		if (updated) {
			if (existing.isPresent()) {
				// Remove old tags/nationality that may no longer be used
				syncTagsOnDelete(existing.get());
				syncNationalityOnDelete(existing.get());
			}
			// Add any new tags/nationality from the updated recipe
			syncTagsOnInsert(recipe);
			syncNationalityOnInsert(recipe);
		}
		return updated;
	}

	// ------------------------------------------------------------------
	// Tag sync helpers
	// ------------------------------------------------------------------

	/**
	 * Adds any tags from the given recipe that aren't already in the tags database.
	 */
	private void syncTagsOnInsert(Recipe recipe) {
		if (!(tagsAccessObject instanceof SqliteTagsDatabaseAccessObject sqliteTags)) return;
		if (recipe.getTags() == null) return;

		List<String> existing = tagsAccessObject.getAllTags();
		for (String tag : recipe.getTags()) {
			if (!existing.contains(tag)) {
				sqliteTags.insertTag(tag);
			}
		}
	}

	/**
	 * Removes any tags from the tags database that no longer appear in any recipe.
	 */
	private void syncTagsOnDelete(Recipe recipe) {
		if (!(tagsAccessObject instanceof SqliteTagsDatabaseAccessObject sqliteTags)) return;
		if (recipe.getTags() == null) return;

		List<String> allRemainingTags = databaseAccessObject.getAllRecipes()
				.stream()
				.filter(r -> !r.getRecipeId().equals(recipe.getRecipeId()))
				.flatMap(r -> r.getTags() != null ? r.getTags().stream() : java.util.stream.Stream.empty())
				.toList();

		for (String tag : recipe.getTags()) {
			if (!allRemainingTags.contains(tag)) {
				sqliteTags.deleteTag(tag);
			}
		}
	}

	// ------------------------------------------------------------------
	// Nationality sync helpers
	// ------------------------------------------------------------------

	/**
	 * Adds the recipe's nationality to the nationalities database if not already present.
	 */
	private void syncNationalityOnInsert(Recipe recipe) {
		if (!(nationalitiesAccessObject instanceof SqliteNationalitiesDatabaseAccessObject sqliteNationalities)) return;
		if (recipe.getNationality() == null || recipe.getNationality().isBlank()) return;

		List<String> existing = nationalitiesAccessObject.getAllNationalities();
		if (!existing.contains(recipe.getNationality())) {
			sqliteNationalities.insertNationality(recipe.getNationality());
		}
	}

	/**
	 * Removes the recipe's nationality from the nationalities database
	 * if no other recipes share that nationality.
	 */
	private void syncNationalityOnDelete(Recipe recipe) {
		if (!(nationalitiesAccessObject instanceof SqliteNationalitiesDatabaseAccessObject sqliteNationalities)) return;
		if (recipe.getNationality() == null || recipe.getNationality().isBlank()) return;

		boolean stillUsed = databaseAccessObject.getAllRecipes()
				.stream()
				.filter(r -> !r.getRecipeId().equals(recipe.getRecipeId()))
				.anyMatch(r -> recipe.getNationality().equals(r.getNationality()));

		if (!stillUsed) {
			sqliteNationalities.deleteNationality(recipe.getNationality());
		}
	}
}