import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { IRecipe } from '../models/recipe.model';
import { RecipesService } from '../services/recipes.service';

@Component({
  selector: 'app-recipe',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './recipe.component.html',
  styleUrl: './recipe.component.css'
})
export class RecipeComponent implements OnInit {

  recipe: IRecipe = {
    recipeId: '',
    title: '',
    rating: 0,
    directions: '',
    nationality: '',
    tags: [],
    ingredients: []
  };

  constructor(
    private route: ActivatedRoute,
    private recipesService: RecipesService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');

      if (id !== null) {
        this.recipesService.getRecipeById(id).subscribe(
          (result: IRecipe) => {
            this.recipe = result;
            console.log('Recipe:', this.recipe);
          },
          (error) => {
            console.error('Error:', error);
          }
        );
      } else {
        console.error("Failed to get the recipe ID");
      }
    });
  }

  editRecipe(): void {
    this.router.navigate(['/add-recipe'], { state: { recipe: this.recipe } });
  }
}