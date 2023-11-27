import { Routes } from "@angular/router";
import { RecipeComponent } from "./recipe.component";


export const RECIPE_ROUTES: Routes = [
    {
        path: ':id',
        component: RecipeComponent
    }
];