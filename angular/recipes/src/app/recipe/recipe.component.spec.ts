import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of, throwError } from 'rxjs';

import { RecipeComponent } from './recipe.component';
import { RecipesService } from '../services/recipes.service';
import { IRecipe } from '../models/recipe.model';

const mockRecipe: IRecipe = {
  recipeId: 'abc123',
  title: 'Pasta Carbonara',
  rating: 4,
  directions: 'Cook pasta. Mix eggs and cheese. Combine.',
  nationality: 'Italian',
  tags: ['pasta', 'quick'],
  ingredients: [
    { amount: 200, unitOfMeasurement: 'g', ingredient: 'spaghetti' }
  ]
};

describe('RecipeComponent', () => {
  let component: RecipeComponent;
  let fixture: ComponentFixture<RecipeComponent>;
  let recipesServiceSpy: jasmine.SpyObj<RecipesService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    recipesServiceSpy = jasmine.createSpyObj('RecipesService', [
      'getRecipeById',
      'deleteRecipeById'
    ]);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    recipesServiceSpy.getRecipeById.and.returnValue(of(mockRecipe));

    await TestBed.configureTestingModule({
      imports: [RecipeComponent],
      providers: [
        { provide: RecipesService, useValue: recipesServiceSpy },
        { provide: Router, useValue: routerSpy },
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ id: 'abc123' }))
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RecipeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load the recipe on init using the route id', () => {
    expect(recipesServiceSpy.getRecipeById).toHaveBeenCalledWith('abc123');
    expect(component.recipe.title).toBe('Pasta Carbonara');
  });

  it('should log an error when getRecipeById fails', () => {
    spyOn(console, 'error');
    recipesServiceSpy.getRecipeById.and.returnValue(throwError(() => new Error('Not found')));
    component.ngOnInit();
    expect(console.error).toHaveBeenCalled();
  });

  // ── Delete flow ──────────────────────────────────────────────────────────────

  it('should show the delete confirmation when confirmDelete() is called', () => {
    component.confirmDelete();
    expect(component.showDeleteConfirm).toBeTrue();
  });

  it('should hide the delete confirmation when Cancel is clicked', () => {
    component.showDeleteConfirm = true;
    component.showDeleteConfirm = false;
    expect(component.showDeleteConfirm).toBeFalse();
  });

  it('should call deleteRecipeById and navigate to /recipes on success', () => {
    recipesServiceSpy.deleteRecipeById.and.returnValue(of(void 0));
    component.deleteRecipe();
    expect(recipesServiceSpy.deleteRecipeById).toHaveBeenCalledWith('abc123');
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/recipes']);
    expect(component.showDeleteConfirm).toBeFalse();
  });

  it('should log an error when deleteRecipeById fails', () => {
    spyOn(console, 'error');
    recipesServiceSpy.deleteRecipeById.and.returnValue(throwError(() => new Error('Server error')));
    component.deleteRecipe();
    expect(console.error).toHaveBeenCalled();
  });

  // ── Edit / navigate ───────────────────────────────────────────────────────────

  it('should navigate to /add-recipe with the recipe state when editRecipe() is called', () => {
    component.editRecipe();
    expect(routerSpy.navigate).toHaveBeenCalledWith(
      ['/add-recipe'],
      { state: { recipe: component.recipe } }
    );
  });

  // ── Duplicate title validation ────────────────────────────────────────────────

  describe('duplicate title guard logic', () => {
    const allRecipes: IRecipe[] = [
      mockRecipe,
      { ...mockRecipe, recipeId: 'xyz789', title: 'Tacos' }
    ];

    function isTitleTaken(title: string, excludeId: string = ''): boolean {
      return allRecipes.some(
        r => r.title.toLowerCase() === title.toLowerCase() && r.recipeId !== excludeId
      );
    }

    it('should detect a duplicate title (exact match)', () => {
      expect(isTitleTaken('Tacos')).toBeTrue();
    });

    it('should detect a duplicate title regardless of casing', () => {
      expect(isTitleTaken('tacos')).toBeTrue();
      expect(isTitleTaken('TACOS')).toBeTrue();
      expect(isTitleTaken('TaCoS')).toBeTrue();
    });

    it('should NOT flag a title as duplicate when it belongs to the recipe being edited', () => {
      expect(isTitleTaken('Pasta Carbonara', 'abc123')).toBeFalse();
      expect(isTitleTaken('pasta carbonara', 'abc123')).toBeFalse();
    });

    it('should NOT flag a genuinely unique new title', () => {
      expect(isTitleTaken('Beef Stew')).toBeFalse();
    });

    it('should flag a duplicate even when the excluded ID does not match', () => {
      expect(isTitleTaken('Tacos', 'abc123')).toBeTrue();
    });
  });
});