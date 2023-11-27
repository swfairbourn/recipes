import { Routes } from '@angular/router';
import { RecipesComponent } from './recipes/recipes.component';
import { MealPlannerComponent } from './meal-planner/meal-planner.component';
import { AddRecipeComponent } from './add-recipe/add-recipe.component';
import { RecipeComponent } from './recipe/recipe.component';

export const routes: Routes = [
    {
        path: 'recipes',
        component: RecipesComponent,
    },
    {
       path: 'recipes',
       loadChildren: () =>
       import('./recipe/recipe.routes').then(r => r.RECIPE_ROUTES)
    },
    {
        path: 'meal-planner',
        title: 'Recipes: Meal Planner',
        component: MealPlannerComponent,
    },
    {
        path: 'add-recipe',
        title: 'Recipes: Add Recipe',
        component: AddRecipeComponent,
    },
    {
        path: '', 
        redirectTo: 'recipes', 
        pathMatch: 'full'
    },

];
