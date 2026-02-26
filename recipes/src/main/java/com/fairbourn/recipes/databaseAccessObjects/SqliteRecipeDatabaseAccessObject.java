package com.fairbourn.recipes.databaseAccessObjects;

import com.fairbourn.recipes.entities.IngredientEntity;
import com.fairbourn.recipes.entities.RecipeEntity;
import com.fairbourn.recipes.model.Ingredient;
import com.fairbourn.recipes.model.Recipe;
import com.fairbourn.recipes.model.UnitOfMeasurement;
import com.fairbourn.recipes.repositories.RecipeRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * SQLite-backed implementation of RecipeDatabaseAccessObject.
 * Active only when the "sqlite" Spring profile is enabled.
 *
 * Translates between the Recipe/Ingredient POJOs used by the service layer
 * and the RecipeEntity/IngredientEntity JPA objects used for persistence.
 */
@Repository
@Qualifier("inMemory") // reuses the same qualifier so RecipesService needs no changes
@Profile("sqlite")
public class SqliteRecipeDatabaseAccessObject implements RecipeDatabaseAccessObject {

    private final RecipeRepository recipeRepository;

    public SqliteRecipeDatabaseAccessObject(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // ------------------------------------------------------------------
    // Public API (implements RecipeDatabaseAccessObject)
    // ------------------------------------------------------------------

    @Override
    public boolean insertRecipe(UUID id, Recipe recipe) {
        try {
            RecipeEntity entity = toEntity(id.toString(), recipe);
            recipeRepository.save(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return new ArrayList<>(recipeRepository.findAll()
                .stream()
                .map(this::toModel)
                .toList());
    }

    @Override
    public Optional<Recipe> getRecipeById(UUID id) {
        return recipeRepository.findById(id.toString())
                .map(this::toModel);
    }

    @Override
    public boolean deleteRecipeById(UUID id) {
        String key = id.toString();
        if (!recipeRepository.existsById(key)) return false;
        recipeRepository.deleteById(key);
        return true;
    }

    @Override
    public boolean updateRecipeById(UUID id, Recipe recipe) {
        String key = id.toString();
        if (!recipeRepository.existsById(key)) return false;
        RecipeEntity entity = toEntity(key, recipe);
        recipeRepository.save(entity);
        return true;
    }

    // ------------------------------------------------------------------
    // Mapping helpers
    // ------------------------------------------------------------------

    /** Convert a Recipe POJO + UUID string into a JPA entity ready for saving. */
    private RecipeEntity toEntity(String id, Recipe recipe) {
        RecipeEntity entity = new RecipeEntity(
                id,
                recipe.getTitle(),
                recipe.getRating(),
                recipe.getDirections(),
                recipe.getNationality()
        );

        // Map ingredients, preserving list order via sortIndex
        List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ing = ingredients.get(i);
                IngredientEntity ingEntity = new IngredientEntity(
                        entity,
                        ing.getAmount(),
                        ing.getUnitOfMeasurement() != null ? ing.getUnitOfMeasurement().name() : null,
                        ing.getIngredient(),
                        i
                );
                entity.getIngredients().add(ingEntity);
            }
        }

        // Map tags
        if (recipe.getTags() != null) {
            entity.setTags(new ArrayList<>(recipe.getTags()));
        }

        return entity;
    }

    /** Convert a JPA entity back into a Recipe POJO for the service layer. */
    private Recipe toModel(RecipeEntity entity) {
        List<Ingredient> ingredients = entity.getIngredients()
                .stream()
                .map(ing -> new Ingredient(
                        ing.getAmount(),
                        ing.getUnitOfMeasurement() != null
                                ? UnitOfMeasurement.valueOf(ing.getUnitOfMeasurement())
                                : null,
                        ing.getIngredient()
                ))
                .toList();

        return new Recipe(
                UUID.fromString(entity.getRecipeId()),
                entity.getTitle(),
                entity.getRating(),
                ingredients,
                entity.getDirections(),
                entity.getNationality(),
                new ArrayList<>(entity.getTags())
        );
    }
}