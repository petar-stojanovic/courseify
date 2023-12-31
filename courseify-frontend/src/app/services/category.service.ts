import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../interfaces/Category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private http: HttpClient) {}

  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`api/category`);
  }

  getCategoriesForCourse(id: number): Observable<Category[]> {
    return this.http.get<Category[]>(`api/course/${id}/categories`);
  }
}
