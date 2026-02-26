import { Injectable } from '@angular/core';
import { IRecipe } from '../models/recipe.model';
import { IIngredient } from '../models/ingredient.model';

@Injectable({
  providedIn: 'root' // singleton — survives route changes
})
export class MealPlannerService {
  menuRecipes: IRecipe[] = [];

  addRecipe(recipe: IRecipe) {
    this.menuRecipes.push(recipe); // duplicates allowed
  }

  removeRecipe(index: number) {
    this.menuRecipes.splice(index, 1);
  }

  clearAll() {
    this.menuRecipes = [];
  }

  getConsolidatedIngredients(): IIngredient[] {
    const map = new Map<string, IIngredient>();
    for (const recipe of this.menuRecipes) {
      for (const ing of recipe.ingredients) {
        const key = `${ing.ingredient}__${ing.unitOfMeasurement}`;
        if (map.has(key)) {
          map.get(key)!.amount += ing.amount;
        } else {
          map.set(key, { ...ing });
        }
      }
    }
    return Array.from(map.values());
  }
}
