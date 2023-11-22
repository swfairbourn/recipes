import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TagsService {

  private baseUrl = 'http://localhost:8080/api/v1/tags';

  constructor(private http: HttpClient) { }

  getAllTags(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/getAllTags`);
  }
}
