import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IRecipe } from '../models/recipe.model';

@Injectable({
  providedIn: 'root'
})
export class RecipesService {

  private baseUrl = 'http://localhost:8080/api/v1/recipes';

  constructor(private http: HttpClient) {}

  getAllRecipes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/getAllRecipes`);
  }

  insertRecipe(recipe: IRecipe): Observable<IRecipe> {
    return this.http.post<IRecipe>(`${this.baseUrl}/insertRecipe`, recipe);
  }

  getRecipeById(recipeId: string): Observable<IRecipe> {
    return this.http.get<IRecipe>(`${this.baseUrl}/${recipeId}`);
  }

  updateRecipe(recipe: IRecipe): Observable<IRecipe> {
    return this.http.put<IRecipe>(`${this.baseUrl}/${recipe.recipeId}`, recipe);
  }

  deleteRecipeById(recipeId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${recipeId}`);
  }

}
