import { Routes } from '@angular/router';
import { RecipesComponent } from './recipes/recipes.component';
import { MealPlannerComponent } from './meal-planner/meal-planner.component';
import { AddRecipeComponent } from './add-recipe/add-recipe.component';

export const routes: Routes = [
    {
        path: '',
        component: RecipesComponent,
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

];
