import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FiltersComponent } from './filters/filters.component';
import { RecipeService } from '../services/recipe.service';
import { IRecipe } from '../models/recipe.model';
import { Observable } from 'rxjs';
import { RecipeCriteria } from '../models/recipe-criteria.model';

@Component({
  selector: 'app-recipes',
  standalone: true,
  imports: [CommonModule, FiltersComponent],
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  recipes$!: Observable<IRecipe[]>;
  recipeCriteria: RecipeCriteria = new RecipeCriteria([], [], []);

  constructor(private recipeService: RecipeService) { }

  ngOnInit() {
    this.recipes$ = this.recipeService.getAllRecipes();
  }

  getAllRecipesMatchingCriteria(criteria: RecipeCriteria) {
    this.recipes$ = this.recipeService.getAllRecipesMatchingCriteria(criteria);
  }
}
