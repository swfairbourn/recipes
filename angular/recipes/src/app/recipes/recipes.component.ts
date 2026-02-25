import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FiltersComponent } from './filters/filters.component';
import { RecipesService } from '../services/recipes.service';
import { IRecipe } from '../models/recipe.model';
import { RecipeCriteria } from '../models/recipe-criteria.model';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

type SortColumn = 'rating' | 'title';
type SortDirection = 'asc' | 'desc';

@Component({
  selector: 'app-recipes',
  standalone: true,
  imports: [CommonModule, FiltersComponent, RouterLink, FormsModule],
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {

  @Input() recipeId = '';
  allRecipes: IRecipe[] = [];
  filteredRecipes: IRecipe[] = [];
  pagedRecipes: IRecipe[] = [];
  searchTerm: string = '';
  recipeCriteria: RecipeCriteria = new RecipeCriteria([], [], []);

  currentPage: number = 1;
  pageSize: number = 10;
  totalPages: number = 1;
  pages: number[] = [];

  sortColumn: SortColumn = 'rating';
  sortDirection: SortDirection = 'desc';

  constructor(private recipesService: RecipesService) { }

  ngOnInit() {
    this.recipesService.getAllRecipes().subscribe(recipes => {
      this.allRecipes = recipes;
      this.filteredRecipes = recipes;
      this.sortRecipes();
      this.updatePagination();
    });
  }

  sortBy(column: SortColumn) {
    if (this.sortColumn === column) {
      // Toggle direction if same column clicked
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      // Default direction per column
      this.sortDirection = column === 'rating' ? 'desc' : 'asc';
    }
    this.sortRecipes();
    this.currentPage = 1;
    this.applyPage();
  }

  sortRecipes() {
    this.filteredRecipes = [...this.filteredRecipes].sort((a, b) => {
      if (this.sortColumn === 'title') {
        const comparison = a.title.localeCompare(b.title);
        return this.sortDirection === 'asc' ? comparison : -comparison;
      } else {
        // rating
        const comparison = a.rating - b.rating;
        return this.sortDirection === 'asc' ? comparison : -comparison;
      }
    });
  }

  getSortIcon(column: SortColumn): string {
    if (this.sortColumn !== column) return 'fa-sort';
    return this.sortDirection === 'asc' ? 'fa-sort-up' : 'fa-sort-down';
  }

  updatePagination() {
    this.totalPages = Math.max(1, Math.ceil(this.filteredRecipes.length / this.pageSize));
    this.pages = Array.from({ length: this.totalPages }, (_, i) => i + 1);
    this.applyPage();
  }

  applyPage() {
    const start = (this.currentPage - 1) * this.pageSize;
    this.pagedRecipes = this.filteredRecipes.slice(start, start + this.pageSize);
  }

  goToPage(page: number) {
    if (page < 1 || page > this.totalPages) return;
    this.currentPage = page;
    this.applyPage();
  }

  applyFilters(criteria: RecipeCriteria) {
    const searchTerm = this.searchTerm.trim().toLowerCase();

    const selectedRatings = criteria.ratings.filter(r => r.selected).map(r => r.value);
    const selectedNationalities = criteria.nationalities.filter(n => n.selected).map(n => n.value);
    const selectedTags = criteria.tags.filter(t => t.selected).map(t => t.value);

    this.filteredRecipes = this.allRecipes.filter(recipe => {
      const matchesSearch = !searchTerm || recipe.title.toLowerCase().includes(searchTerm);
      const matchesRating = selectedRatings.length === 0 || selectedRatings.some(r => r === recipe.rating);
      const matchesNationality = selectedNationalities.length === 0 || selectedNationalities.some(n => n === recipe.nationality);
      const matchesTags = selectedTags.length === 0 || selectedTags.some(tag => recipe.tags.includes(tag));

      return matchesSearch && matchesRating && matchesNationality && matchesTags;
    });

    this.sortRecipes();
    this.currentPage = 1;
    this.updatePagination();
  }

  onSearchChange() {
    this.applyFilters(this.recipeCriteria);
  }

}