import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

import { AddRecipeComponent } from './add-recipe.component';
import { RecipesService } from '../services/recipes.service';
import { RecipeService } from '../services/recipe.service';
import { NationalitiesService } from '../services/nationalities.service';
import { TagsService } from '../services/tags.service';
import { IRecipe } from '../models/recipe.model';

// ── Shared test data ──────────────────────────────────────────────────────────

const existingRecipe: IRecipe = {
  recipeId: 'abc123',
  title: 'Pasta Carbonara',
  rating: 4,
  directions: 'Cook pasta.',
  nationality: 'Italian',
  tags: ['pasta'],
  ingredients: [{ amount: 200, unitOfMeasurement: 'g', ingredient: 'spaghetti' }]
};

const otherRecipe: IRecipe = {
  recipeId: 'xyz789',
  title: 'Tacos',
  rating: 5,
  directions: 'Make tacos.',
  nationality: 'Mexican',
  tags: ['mexican'],
  ingredients: []
};

const allRecipes: IRecipe[] = [existingRecipe, otherRecipe];

// ── Helpers ───────────────────────────────────────────────────────────────────

function buildSpies() {
  const recipesService = jasmine.createSpyObj('RecipesService', [
    'getRecipes',
    'insertRecipe',
    'updateRecipe'
  ]);
  const recipeService = jasmine.createSpyObj('RecipeService', ['getAllUnitsOfMeasurement']);
  const nationalitiesService = jasmine.createSpyObj('NationalitiesService', ['getAllNationalities']);
  const tagsService = jasmine.createSpyObj('TagsService', ['getAllTags']);
  const router = jasmine.createSpyObj('Router', ['navigate', 'getCurrentNavigation']);

  recipesService.getRecipes.and.returnValue(of(allRecipes));
  recipesService.insertRecipe.and.returnValue(of({}));
  recipesService.updateRecipe.and.returnValue(of({}));
  recipeService.getAllUnitsOfMeasurement.and.returnValue(of(new Map()));
  nationalitiesService.getAllNationalities.and.returnValue(of([]));
  tagsService.getAllTags.and.returnValue(of([]));
  router.getCurrentNavigation.and.returnValue(null);

  return { recipesService, recipeService, nationalitiesService, tagsService, router };
}

async function createComponent(spies: ReturnType<typeof buildSpies>) {
  await TestBed.configureTestingModule({
    imports: [AddRecipeComponent],
    providers: [
      { provide: RecipesService, useValue: spies.recipesService },
      { provide: RecipeService, useValue: spies.recipeService },
      { provide: NationalitiesService, useValue: spies.nationalitiesService },
      { provide: TagsService, useValue: spies.tagsService },
      { provide: Router, useValue: spies.router }
    ]
  }).compileComponents();

  const fixture = TestBed.createComponent(AddRecipeComponent);
  const component = fixture.componentInstance;
  fixture.detectChanges();
  return { fixture, component };
}

// ── Tests ─────────────────────────────────────────────────────────────────────

describe('AddRecipeComponent', () => {

  // ── Bootstrap ───────────────────────────────────────────────────────────────

  describe('initialization', () => {
    it('should create with default empty state', async () => {
      const spies = buildSpies();
      const { component } = await createComponent(spies);
      expect(component).toBeTruthy();
      expect(component.isEditing).toBeFalse();
      expect(component.title).toBe('');
      expect(component.titleError).toBe('');
    });

    it('should pre-populate fields and set isEditing when a recipe is passed via router state', async () => {
      const spies = buildSpies();
      spies.router.getCurrentNavigation.and.returnValue({
        extras: { state: { recipe: existingRecipe } }
      } as any);
      const { component } = await createComponent(spies);

      expect(component.isEditing).toBeTrue();
      expect(component.recipeId).toBe('abc123');
      expect(component.title).toBe('Pasta Carbonara');
      expect(component.rating).toBe(4);
      expect(component.nationality).toBe('Italian');
    });
  });

  // ── Ingredient helpers ───────────────────────────────────────────────────────

  describe('ingredient management', () => {
    it('should add a blank ingredient row', async () => {
      const { component } = await createComponent(buildSpies());
      component.addIngredient();
      expect(component.ingredients.length).toBe(1);
      expect(component.ingredients[0].ingredient).toBe('');
    });

    it('should remove an ingredient by index', async () => {
      const { component } = await createComponent(buildSpies());
      component.addIngredient();
      component.addIngredient();
      component.removeIngredient(0);
      expect(component.ingredients.length).toBe(1);
    });
  });

  // ── Tag helpers ──────────────────────────────────────────────────────────────

  describe('tag management', () => {
    it('should add a blank tag', async () => {
      const { component } = await createComponent(buildSpies());
      component.addTag();
      expect(component.tags.length).toBe(1);
    });

    it('should remove a tag by index', async () => {
      const { component } = await createComponent(buildSpies());
      component.addTag();
      component.addTag();
      component.removeTag(0);
      expect(component.tags.length).toBe(1);
    });
  });

  // ── titleError reset ─────────────────────────────────────────────────────────

  describe('onTitleChange()', () => {
    it('should clear titleError when the title is changed', async () => {
      const { component } = await createComponent(buildSpies());
      component.titleError = 'Some error';
      component.onTitleChange();
      expect(component.titleError).toBe('');
    });
  });

  // ── saveRecipe — happy paths ─────────────────────────────────────────────────

  describe('saveRecipe() — success', () => {
    it('should call insertRecipe for a new recipe with a unique title', fakeAsync(async () => {
      const spies = buildSpies();
      const { component } = await createComponent(spies);

      component.title = 'Brand New Recipe';
      component.saveRecipe();
      tick(1500);

      expect(spies.recipesService.getRecipes).toHaveBeenCalled();
      expect(spies.recipesService.insertRecipe).toHaveBeenCalled();
      expect(component.saveError).toBeFalse();
      expect(component.titleError).toBe('');
      expect(spies.router.navigate).toHaveBeenCalledWith(['/recipes']);
    }));

    it('should call updateRecipe when editing an existing recipe', fakeAsync(async () => {
      const spies = buildSpies();
      spies.router.getCurrentNavigation.and.returnValue({
        extras: { state: { recipe: existingRecipe } }
      } as any);
      const { component } = await createComponent(spies);

      // Title unchanged — same ID, so not a duplicate
      component.saveRecipe();
      tick(1500);

      expect(spies.recipesService.updateRecipe).toHaveBeenCalled();
      expect(spies.recipesService.insertRecipe).not.toHaveBeenCalled();
      expect(component.titleError).toBe('');
    }));

    it('should allow editing a recipe to a completely new unique title', fakeAsync(async () => {
      const spies = buildSpies();
      spies.router.getCurrentNavigation.and.returnValue({
        extras: { state: { recipe: existingRecipe } }
      } as any);
      const { component } = await createComponent(spies);

      component.title = 'Updated Unique Title';
      component.saveRecipe();
      tick(1500);

      expect(spies.recipesService.updateRecipe).toHaveBeenCalled();
      expect(component.titleError).toBe('');
    }));
  });

  // ── saveRecipe — duplicate title guard ───────────────────────────────────────

  describe('saveRecipe() — duplicate title validation', () => {
    it('should set titleError and NOT save when a new recipe title matches an existing one exactly', async () => {
      const spies = buildSpies();
      const { component } = await createComponent(spies);

      component.title = 'Tacos';
      component.saveRecipe();

      expect(component.titleError).toBe(
        'A recipe with this title already exists. Please choose a unique title.'
      );
      expect(spies.recipesService.insertRecipe).not.toHaveBeenCalled();
    });

    it('should set titleError when the duplicate differs only in casing (all lower)', async () => {
      const spies = buildSpies();
      const { component } = await createComponent(spies);

      component.title = 'tacos';
      component.saveRecipe();

      expect(component.titleError).toBeTruthy();
      expect(spies.recipesService.insertRecipe).not.toHaveBeenCalled();
    });

    it('should set titleError when the duplicate differs only in casing (all upper)', async () => {
      const spies = buildSpies();
      const { component } = await createComponent(spies);

      component.title = 'TACOS';
      component.saveRecipe();

      expect(component.titleError).toBeTruthy();
      expect(spies.recipesService.insertRecipe).not.toHaveBeenCalled();
    });

    it('should set titleError when the duplicate differs only in casing (mixed)', async () => {
      const spies = buildSpies();
      const { component } = await createComponent(spies);

      component.title = 'TaCoS';
      component.saveRecipe();

      expect(component.titleError).toBeTruthy();
      expect(spies.recipesService.insertRecipe).not.toHaveBeenCalled();
    });

    it('should set titleError when editing and the new title matches a DIFFERENT existing recipe', async () => {
      const spies = buildSpies();
      // Editing "Pasta Carbonara" but renaming it to "Tacos" (which already exists)
      spies.router.getCurrentNavigation.and.returnValue({
        extras: { state: { recipe: existingRecipe } }
      } as any);
      const { component } = await createComponent(spies);

      component.title = 'Tacos';
      component.saveRecipe();

      expect(component.titleError).toBeTruthy();
      expect(spies.recipesService.updateRecipe).not.toHaveBeenCalled();
    });

    it('should set titleError when editing and the new title matches a different recipe case-insensitively', async () => {
      const spies = buildSpies();
      spies.router.getCurrentNavigation.and.returnValue({
        extras: { state: { recipe: existingRecipe } }
      } as any);
      const { component } = await createComponent(spies);

      component.title = 'TACOS';
      component.saveRecipe();

      expect(component.titleError).toBeTruthy();
      expect(spies.recipesService.updateRecipe).not.toHaveBeenCalled();
    });

    it('should NOT flag the title when editing and keeping the same title (case-insensitive self-match)', async () => {
      const spies = buildSpies();
      spies.router.getCurrentNavigation.and.returnValue({
        extras: { state: { recipe: existingRecipe } }
      } as any);
      const { component } = await createComponent(spies);

      // Same title, same ID — should be allowed
      component.title = 'pasta carbonara';
      component.saveRecipe();

      expect(component.titleError).toBe('');
    });

    it('should set titleError and NOT save when title matches after trimming whitespace', async () => {
      const spies = buildSpies();
      const { component } = await createComponent(spies);

      component.title = '  Tacos  ';
      component.saveRecipe();

      expect(component.titleError).toBeTruthy();
      expect(spies.recipesService.insertRecipe).not.toHaveBeenCalled();
    });
  });

  // ── saveRecipe — error handling ──────────────────────────────────────────────

  describe('saveRecipe() — error handling', () => {
    it('should show a saveMessage error when getRecipes fails', async () => {
      const spies = buildSpies();
      spies.recipesService.getRecipes.and.returnValue(throwError(() => new Error('Network error')));
      const { component } = await createComponent(spies);

      component.title = 'Some New Recipe';
      component.saveRecipe();

      expect(component.saveError).toBeTrue();
      expect(component.saveMessage).toBe('Failed to save recipe. Please try again.');
    });

    it('should show a saveMessage error when insertRecipe fails', fakeAsync(async () => {
      const spies = buildSpies();
      spies.recipesService.insertRecipe.and.returnValue(throwError(() => new Error('Save failed')));
      const { component } = await createComponent(spies);

      component.title = 'Brand New Recipe';
      component.saveRecipe();
      tick();

      expect(component.saveError).toBeTrue();
      expect(component.saveMessage).toBe('Failed to save recipe. Please try again.');
    }));

    it('should show a saveMessage error when updateRecipe fails', fakeAsync(async () => {
      const spies = buildSpies();
      spies.router.getCurrentNavigation.and.returnValue({
        extras: { state: { recipe: existingRecipe } }
      } as any);
      spies.recipesService.updateRecipe.and.returnValue(throwError(() => new Error('Update failed')));
      const { component } = await createComponent(spies);

      component.saveRecipe();
      tick();

      expect(component.saveError).toBeTrue();
      expect(component.saveMessage).toBe('Failed to save recipe. Please try again.');
    }));
  });
});