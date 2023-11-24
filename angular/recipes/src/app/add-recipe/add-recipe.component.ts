import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecipeService } from '../services/recipe.service';
import { Observable } from 'rxjs';
import { NationalitiesService } from '../services/nationalities.service';
import { FormsModule } from '@angular/forms';
import { IIngredient } from '../models/ingredient.model';
import { TagsService } from '../services/tags.service';
import { IRecipe } from '../models/recipe.model';
import { RecipesService } from '../services/recipes.service';

@Component({
  selector: 'app-add-recipe',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-recipe.component.html',
  styleUrl: './add-recipe.component.css'
})
export class AddRecipeComponent implements OnInit {

  title: string;
  rating: number;
  ingredients: IIngredient[];
  directions: string;
  nationality: string;
  tags: any[];

  unitsOfMeasurement$! : Observable<Map<string, string>>;
  nationalities$! : Observable<string[]>;
  tags$! : Observable<string[]>;

  constructor(
    private recipesService: RecipesService,
    private recipeService: RecipeService, 
    private nationalitiesService: NationalitiesService,
    private tagsService: TagsService
  ) {
    this.title = "";
    this.rating = 0;
    this.ingredients = [];
    this.directions = "";
    this.nationality = "";
    this.tags = [];
  }

  ngOnInit(): void {
    this.unitsOfMeasurement$ = this.recipeService.getAllUnitsOfMeasurement();
    this.nationalities$ = this.nationalitiesService.getAllNationalities();
    this.tags$ = this.tagsService.getAllTags();
  }

  addIngredient(): void {
    const newIngredient: IIngredient = {
      amount: 0,
      unitOfMeasurement: '',
      ingredient: '',
    };
    this.ingredients.push(newIngredient);
  }

  removeIngredient(index: number) {
    this.ingredients.splice(index, 1);
  }

  addTag() {

    this.tags.push('');
  }

  removeTag(index: number) {
    this.tags.splice(index, 1);
  }

  trackByIndex(index: number, item: any): number {
    return index;
  }

  saveRecipe() {
    const newRecipe: IRecipe = {
      title: this.title,
      rating: this.rating,
      ingredients: this.ingredients,
      directions: this.directions,
      nationality: this.nationality,
      tags: this.tags
    }
    console.log(newRecipe);
    this.recipesService.insertRecipe(newRecipe).subscribe(
      response => {
        console.log('Recipe inserted successfully', response);
      },
      error => {
        console.error('Error inserting recipe', error);
      }
    );
  }

}
