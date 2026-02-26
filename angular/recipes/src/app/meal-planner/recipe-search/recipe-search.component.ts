import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IRecipe } from '../../models/recipe.model';

@Component({
  selector: 'app-recipe-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './recipe-search.component.html',
})
export class RecipeSearchComponent {
  @Input() allRecipes: IRecipe[] = [];
  @Output() recipeSelected = new EventEmitter<IRecipe>();

  searchTerm: string = '';
  filteredRecipes: IRecipe[] = [];
  showDropdown: boolean = false;

  onSearchInput() {
    const term = this.searchTerm.trim().toLowerCase();
    if (!term) {
      this.filteredRecipes = [];
      this.showDropdown = false;
      return;
    }
    this.filteredRecipes = this.allRecipes
      .filter(r => r.title.toLowerCase().includes(term))
      .slice(0, 8); // cap results for usability
    this.showDropdown = this.filteredRecipes.length > 0;
  }

  selectRecipe(recipe: IRecipe) {
    this.recipeSelected.emit(recipe);
    this.searchTerm = '';
    this.filteredRecipes = [];
    this.showDropdown = false;
  }

  closeDropdown() {
    // slight delay so click on item registers before blur hides it
    setTimeout(() => this.showDropdown = false, 150);
  }
}
