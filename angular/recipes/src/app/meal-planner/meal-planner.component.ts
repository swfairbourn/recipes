import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecipesService } from '../services/recipes.service';
import { MealPlannerService } from '../services/meal-planner.service';
import { IRecipe } from '../models/recipe.model';
import { IIngredient } from '../models/ingredient.model';
import { RecipeSearchComponent } from './recipe-search/recipe-search.component';

@Component({
  selector: 'app-meal-planner',
  standalone: true,
  imports: [CommonModule, RecipeSearchComponent],
  templateUrl: './meal-planner.component.html',
  styleUrl: './meal-planner.component.css'
})
export class MealPlannerComponent implements OnInit {
  private recipesService = inject(RecipesService);
  private mealPlannerService = inject(MealPlannerService);

  allRecipes: IRecipe[] = [];

  ngOnInit() {
    this.recipesService.getAllRecipes().subscribe(recipes => {
      this.allRecipes = recipes;
    });
  }

  get menuRecipes(): IRecipe[] {
    return this.mealPlannerService.menuRecipes;
  }

  get consolidatedIngredients(): IIngredient[] {
    return this.mealPlannerService.getConsolidatedIngredients();
  }

  onRecipeSelected(recipe: IRecipe) {
    this.mealPlannerService.addRecipe(recipe);
  }

  removeRecipe(index: number) {
    this.mealPlannerService.removeRecipe(index);
  }

  clearAll() {
    this.mealPlannerService.clearAll();
  }
}