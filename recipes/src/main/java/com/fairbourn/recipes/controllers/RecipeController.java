package com.fairbourn.recipes.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fairbourn.recipes.model.UnitOfMeasurement;
import com.fairbourn.recipes.services.RecipeService;

@RequestMapping("api/v1/recipe")
@RestController
public class RecipeController {

	private final RecipeService recipeService;
	
	@Autowired
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/getAllUnitsOfMeasurement")
	public Map<String, String> getAllUnitOfMeasurements() {
        Map<String, String> unitMap = new HashMap<>();
        for (UnitOfMeasurement unit : UnitOfMeasurement.values()) {
            unitMap.put(unit.name(), unit.getLabel());
        }
        return unitMap;
    }
}
