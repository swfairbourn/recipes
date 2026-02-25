import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FiltersComponent } from './filters/filters.component';
import { RecipesService } from '../services/recipes.service';
import { IRecipe } from '../models/recipe.model';
import { RecipeCriteria } from '../models/recipe-criteria.model';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

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

  constructor(private recipesService: RecipesService) { }

  ngOnInit() {
    this.recipesService.getAllRecipes().subscribe(recipes => {
      this.allRecipes = recipes;
      this.filteredRecipes = recipes;
      this.updatePagination();
    });
  }

  onSearchChange() {
    const term = this.searchTerm.trim().toLowerCase();
    this.filteredRecipes = term
      ? this.allRecipes.filter(r => r.title.toLowerCase().includes(term))
      : [...this.allRecipes];
    this.currentPage = 1;
    this.updatePagination();
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

  getAllRecipesMatchingCriteria(criteria: RecipeCriteria) {
    this.recipesService.getAllRecipesMatchingCriteria(criteria).subscribe(recipes => {
      this.allRecipes = recipes;
      this.filteredRecipes = recipes;
      this.currentPage = 1;
      this.updatePagination();
    });
  }

}