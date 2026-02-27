import { Injectable } from '@angular/core';
import { IRecipe } from '../models/recipe.model';
import { IIngredient } from '../models/ingredient.model';

export interface IIngredientRow extends IIngredient {
  checked: boolean;
  key: string;
}

@Injectable({
  providedIn: 'root' // singleton — survives route changes
})
export class MealPlannerService {
  menuRecipes: IRecipe[] = [];
  private deselectedKeys = new Set<string>();

  addRecipe(recipe: IRecipe) {
    this.menuRecipes.push(recipe); // duplicates allowed
  }

  removeRecipe(index: number) {
    this.menuRecipes.splice(index, 1);
  }

  clearAll() {
    this.menuRecipes = [];
    this.deselectedKeys.clear();
  }

  toggleIngredient(key: string, checked: boolean) {
    if (checked) {
      this.deselectedKeys.delete(key);
    } else {
      this.deselectedKeys.add(key);
    }
  }

  getConsolidatedIngredients(): IIngredientRow[] {
    const map = new Map<string, IIngredientRow>();
    for (const recipe of this.menuRecipes) {
      for (const ing of recipe.ingredients) {
        const key = `${ing.ingredient}__${ing.unitOfMeasurement}`;
        if (map.has(key)) {
          map.get(key)!.amount += ing.amount;
        } else {
          map.set(key, {
            ...ing,
            key,
            // preserve existing deselected state, default to checked
            checked: !this.deselectedKeys.has(key)
          });
        }
      }
    }
    return Array.from(map.values());
  }
}