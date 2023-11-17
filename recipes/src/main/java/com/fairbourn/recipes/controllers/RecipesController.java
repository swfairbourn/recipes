package com.fairbourn.recipes.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fairbourn.recipes.model.Recipe;
import com.fairbourn.recipes.services.RecipesService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("api/v1/recipes")
@RestController
public class RecipesController {

	private final RecipesService recipesService;

	@Autowired
	public RecipesController(RecipesService recipesService) {
		this.recipesService = recipesService;
	}

	@PostMapping("/insertRecipe")
	public void insertRecipe(@RequestBody Recipe recipe, HttpServletResponse response) {
		boolean inserted = recipesService.insertRecipe(recipe);
		if (inserted) {
			response.setStatus(HttpServletResponse.SC_CREATED);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to insert recipe");
		}
	}

	@GetMapping("/getAllRecipes")
	public List<Recipe> getAllRecipes() {
		return recipesService.getAllRecipes();
	}

	@GetMapping(path = "/{id}")
	public Recipe getRecipeById(@PathVariable("id") UUID id) {
		Optional<Recipe> recipe = recipesService.getRecipeById(id);
		if (recipe.isPresent()) {
			return recipe.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
		}
	}

	@DeleteMapping(path = "/{id}")
	public void deleteRecipeById(@PathVariable("id") UUID id) {
		recipesService.deleteRecipeById(id);
	}

	@PutMapping(path = "/{id}")
	public void updateRecipeById(@PathVariable("id") UUID id, @RequestBody Recipe recipe,
			HttpServletResponse response) {
		boolean updated = recipesService.updateRecipeById(id, recipe);
		if (updated) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to insert recipe");
		}
	}
}
