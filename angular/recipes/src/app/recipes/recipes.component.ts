import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SortingComponent } from './sorting/sorting.component';
import { RecipeService } from './recipe.service';

@Component({
  selector: 'app-recipes',
  standalone: true,
  imports: [CommonModule, SortingComponent],
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  recipes: any[] = []; // Adjust the type based on your actual Recipe model

  constructor(private recipeService: RecipeService) {}

  ngOnInit() {
    this.getRecipes();
  }

  getRecipes() {
    this.recipeService.getAllRecipes().subscribe(
      (data: any[]) => {
        this.recipes = data;
      },
      (error) => {
        console.error('Error fetching recipes:', error);
      }
    );
  }
}
