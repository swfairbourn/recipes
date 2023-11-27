import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FiltersComponent } from './filters/filters.component';
import { RecipesService } from '../services/recipes.service';
import { IRecipe } from '../models/recipe.model';
import { Observable } from 'rxjs';
import { RecipeCriteria } from '../models/recipe-criteria.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-recipes',
  standalone: true,
  imports: [CommonModule, FiltersComponent, RouterLink],
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  @Input() recipeId = '';
  recipes$!: Observable<IRecipe[]>;
  recipeCriteria: RecipeCriteria = new RecipeCriteria([], [], []);

  constructor(private recipesService: RecipesService) { }

  ngOnInit() {
    this.recipes$ = this.recipesService.getAllRecipes();
  }

  getAllRecipesMatchingCriteria(criteria: RecipeCriteria) {
    this.recipes$ = this.recipesService.getAllRecipesMatchingCriteria(criteria);
  }

}
