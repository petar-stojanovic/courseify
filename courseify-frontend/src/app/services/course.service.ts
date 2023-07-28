import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { Course } from '../interfaces/course';
import { Category } from '../interfaces/category';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  constructor(private http: HttpClient) {}

  getCourses(search?: string, categoryName?: string): Observable<Course[]> {

    let queryParams = new HttpParams();
    if(search && categoryName){
      queryParams = queryParams.append("search", search);
      queryParams = queryParams.append("categoryName", categoryName);
    }else if (search){
      queryParams = queryParams.append("search", search);
    }else if (categoryName){
      queryParams = queryParams.append("categoryName", categoryName);
    }
    
    return this.http.get<Course[]>(`/api/course`, {params: queryParams});
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>('/api/category');
  }

  deleteCourse(id: number): Observable<Course> {
    return this.http.delete<Course>(`/api/course/${id}`);
  }

  addCourse(data: FormData): Observable<Course> {
    return this.http.post<Course>(`/api/course/save`, data);
  }
  
  editCourse(id: number, data: FormData): Observable<Course>{
    return this.http.put<Course>(`api/course/${id}`, data)
  }

  getCourseById(id: number): Observable<Course>{
    return this.http.get<Course>(`api/course/${id}`)
  }
}
