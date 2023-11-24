import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  private baseUrl = 'http://localhost:8080/api/v1/recipe';

  constructor(private http: HttpClient) {}

  getAllUnitsOfMeasurement(): Observable<Map<string, string>> {
    return this.http.get<Map<string, string>>(`${this.baseUrl}/getAllUnitsOfMeasurement`);
  }
}
