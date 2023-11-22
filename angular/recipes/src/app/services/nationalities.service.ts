import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NationalitiesService {

  private baseUrl = 'http://localhost:8080/api/v1/nationalities';

  constructor(private http: HttpClient) { }

  getAllNationalities(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/getAllNationalities`);
  }
}
