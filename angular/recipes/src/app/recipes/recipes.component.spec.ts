import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { Component, EventEmitter, Input, Output } from '@angular/core';

import { RecipesComponent } from './recipes.component';
import { RecipesService } from '../services/recipes.service';
import { IRecipe } from '../models/recipe.model';
import { RecipeCriteria } from '../models/recipe-criteria.model';
import { RouterModule } from '@angular/router';
import { FiltersComponent } from './filters/filters.component';

// Prevents FiltersComponent's own dependency chain from causing injection errors
@Component({ selector: 'app-filters', standalone: true, template: '' })
class MockFiltersComponent {
  @Input() recipeCriteria!: RecipeCriteria;
  @Output() filtersChanged = new EventEmitter<RecipeCriteria>();
}

const mockRecipes: IRecipe[] = [
  {
    recipeId: '1',
    title: 'Pasta Carbonara',
    rating: 4,
    directions: 'Cook pasta.',
    nationality: 'Italian',
    tags: ['pasta'],
    ingredients: []
  },
  {
    recipeId: '2',
    title: 'Tacos',
    rating: 5,
    directions: 'Make tacos.',
    nationality: 'Mexican',
    tags: ['mexican'],
    ingredients: []
  }
];

describe('RecipesComponent', () => {
  let component: RecipesComponent;
  let fixture: ComponentFixture<RecipesComponent>;
  let recipesServiceSpy: jasmine.SpyObj<RecipesService>;

  beforeEach(async () => {
    recipesServiceSpy = jasmine.createSpyObj('RecipesService', ['getAllRecipes']);
    recipesServiceSpy.getAllRecipes.and.returnValue(of(mockRecipes));

    await TestBed.configureTestingModule({
      imports: [RecipesComponent, RouterModule.forRoot([])],
      providers: [
        { provide: RecipesService, useValue: recipesServiceSpy }
      ]
    })
    .overrideComponent(RecipesComponent, {
      remove: { imports: [FiltersComponent] },
      add: { imports: [MockFiltersComponent] }
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load all recipes on init', () => {
    expect(recipesServiceSpy.getAllRecipes).toHaveBeenCalled();
    expect(component.allRecipes.length).toBe(2);
  });

  it('should default sort by rating descending', () => {
    expect(component.sortColumn).toBe('rating');
    expect(component.sortDirection).toBe('desc');
  });

  it('should toggle sort direction when the same column is clicked twice', () => {
    component.sortBy('rating');
    expect(component.sortDirection).toBe('asc');
    component.sortBy('rating');
    expect(component.sortDirection).toBe('desc');
  });

  it('should switch column and reset direction when a new column is clicked', () => {
    component.sortBy('title');
    expect(component.sortColumn).toBe('title');
    expect(component.sortDirection).toBe('asc');
  });

  it('should filter recipes by search term', () => {
    component.searchTerm = 'tacos';
    component.onSearchChange();
    expect(component.filteredRecipes.length).toBe(1);
    expect(component.filteredRecipes[0].title).toBe('Tacos');
  });

  it('should return all recipes when search term is cleared', () => {
    component.searchTerm = 'tacos';
    component.onSearchChange();
    component.searchTerm = '';
    component.onSearchChange();
    expect(component.filteredRecipes.length).toBe(2);
  });

  it('should paginate correctly with default page size', () => {
    expect(component.currentPage).toBe(1);
    expect(component.pagedRecipes.length).toBeLessThanOrEqual(component.pageSize);
  });

  it('should not navigate to an invalid page', () => {
    component.goToPage(0);
    expect(component.currentPage).toBe(1);
    component.goToPage(999);
    expect(component.currentPage).toBe(1);
  });

  it('should return the correct sort icon class', () => {
    component.sortColumn = 'rating';
    component.sortDirection = 'asc';
    expect(component.getSortIcon('rating')).toBe('fa-sort-up');
    expect(component.getSortIcon('title')).toBe('fa-sort');
  });
});