import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  private baseUrl = 'http://localhost:8080/api/v1/recipes';

  constructor(private http: HttpClient) {}

  getAllRecipes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/getAllRecipes`);
  }
}
