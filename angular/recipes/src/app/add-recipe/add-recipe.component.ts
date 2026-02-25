import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
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

  recipeId: string;
  title: string;
  rating: number;
  ingredients: IIngredient[];
  directions: string;
  nationality: string;
  tags: any[];
  isEditing: boolean = false;
  saveMessage: string = '';
  saveError: boolean = false;

  unitsOfMeasurement$!: Observable<Map<string, string>>;
  nationalities$!: Observable<string[]>;
  tags$!: Observable<string[]>;

  constructor(
    private recipesService: RecipesService,
    private recipeService: RecipeService,
    private nationalitiesService: NationalitiesService,
    private tagsService: TagsService,
    private router: Router
  ) {
    this.recipeId = "";
    this.title = "";
    this.rating = 0;
    this.ingredients = [];
    this.directions = "";
    this.nationality = "";
    this.tags = [];

    // Check if a recipe was passed via router state for editing
    const navigation = this.router.getCurrentNavigation();
    const state = navigation?.extras?.state as { recipe: IRecipe };
    if (state?.recipe) {
      const recipe = state.recipe;
      this.recipeId = recipe.recipeId;
      this.title = recipe.title;
      this.rating = recipe.rating;
      this.ingredients = recipe.ingredients;
      this.directions = recipe.directions;
      this.nationality = recipe.nationality;
      this.tags = recipe.tags;
      this.isEditing = true;
    }
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
      recipeId: this.recipeId,
      title: this.title,
      rating: this.rating,
      ingredients: this.ingredients,
      directions: this.directions,
      nationality: this.nationality,
      tags: this.tags
    };

    const operation$ = this.isEditing
      ? this.recipesService.updateRecipe(newRecipe)
      : this.recipesService.insertRecipe(newRecipe);

    operation$.subscribe({
      next: (response) => {
        console.log('Recipe saved successfully', response);
        this.saveMessage = 'Recipe saved successfully!';
        this.saveError = false;
        setTimeout(() => this.router.navigate(['/recipes']), 1500);
      },
      error: (error) => {
        console.error('Error saving recipe', error);
        this.saveMessage = 'Failed to save recipe. Please try again.';
        this.saveError = true;
      }
    });
  }
}