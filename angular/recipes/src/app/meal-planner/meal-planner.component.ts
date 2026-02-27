import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecipesService } from '../services/recipes.service';
import { MealPlannerService, IIngredientRow } from '../services/meal-planner.service';
import { IRecipe } from '../models/recipe.model';
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

  get consolidatedIngredients(): IIngredientRow[] {
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

  onIngredientToggle(key: string, event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    this.mealPlannerService.toggleIngredient(key, checked);
  }

  generatePdf() {
    const menuRows = this.menuRecipes
      .map(r => `<tr><td>${r.title}</td></tr>`)
      .join('');

    const ingredientRows = this.consolidatedIngredients
      .filter(i => i.checked)
      .map(i => `<tr><td>${i.ingredient}</td><td>${i.amount}</td><td>${i.unitOfMeasurement}</td></tr>`)
      .join('');

    const html = `
      <!DOCTYPE html>
      <html>
        <head>
          <title>Meal Planner</title>
          <style>
            body {
              font-family: Arial, sans-serif;
              padding: 32px;
              color: #111;
            }
            h1 {
              text-align: center;
              font-size: 22px;
              margin-bottom: 24px;
            }
            h2 {
              font-size: 16px;
              margin: 20px 0 8px 0;
              border-bottom: 1px solid #ccc;
              padding-bottom: 4px;
            }
            table {
              width: 100%;
              border-collapse: collapse;
              margin-bottom: 16px;
            }
            th {
              text-align: left;
              font-size: 12px;
              font-weight: bold;
              border-bottom: 2px solid #333;
              padding: 4px 8px;
            }
            .menu-table td {
              font-size: 13px;
              padding: 5px 8px;
              border-bottom: 1px solid #e0e0e0;
            }
            .ingredients-table td {
              font-size: 11px;
              padding: 2px 8px;
              border-bottom: 1px solid #efefef;
            }
            @media print {
              body { padding: 16px; }
            }
          </style>
        </head>
        <body>
          <h1>Meal Planner</h1>

          <h2>Menu</h2>
          <table class="menu-table">
            <thead>
              <tr><th>Recipe</th></tr>
            </thead>
            <tbody>
              ${menuRows}
            </tbody>
          </table>

          <h2>Ingredients</h2>
          <table class="ingredients-table">
            <thead>
              <tr><th>Ingredient</th><th>Amount</th><th>Unit</th></tr>
            </thead>
            <tbody>
              ${ingredientRows}
            </tbody>
          </table>

          <script>
            window.onload = function() { window.print(); }
          </script>
        </body>
      </html>
    `;

    const tab = window.open('', '_blank');
    if (tab) {
      tab.document.write(html);
      tab.document.close();
    }
  }
}