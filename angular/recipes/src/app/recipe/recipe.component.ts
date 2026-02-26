import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { IRecipe } from '../models/recipe.model';
import { RecipesService } from '../services/recipes.service';
import { Pipe, PipeTransform } from '@angular/core';
import { FractionPipe } from '../pipes/fraction.pipe';

@Pipe({
  name: 'formatUnit',
  standalone: true
})
export class FormatUnitPipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';
    return value
      .toLowerCase()
      .split('_')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }
}

@Component({
  selector: 'app-recipe',
  standalone: true,
  imports: [CommonModule, FormatUnitPipe, FractionPipe],
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

  showDeleteConfirm: boolean = false;

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

  confirmDelete(): void {
    this.showDeleteConfirm = true;
  }

  deleteRecipe(): void {
    this.recipesService.deleteRecipeById(this.recipe.recipeId).subscribe({
      next: () => {
        this.showDeleteConfirm = false;
        this.router.navigate(['/recipes']);
      },
      error: (error) => {
        console.error('Error deleting recipe', error);
      }
    });
  }

}
