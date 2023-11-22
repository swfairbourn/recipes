import { Routes } from '@angular/router';
import { RecipesComponent } from './recipes/recipes.component';
import { MealPlannerComponent } from './meal-planner/meal-planner.component';

export const routes: Routes = [
    {
        path: '',
        component: RecipesComponent,
    },
    {
        path: 'meal-planner',
        component: MealPlannerComponent,
    },

];
