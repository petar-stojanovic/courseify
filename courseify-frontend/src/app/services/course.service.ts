import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Course } from '../interfaces/course';
import { courseRequest } from '../interfaces/courseRequest';
import { Category } from '../interfaces/category';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  constructor(private http: HttpClient) {}

  getCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`/api/course`);
  }

  getLessonsByCourseId(id: number): Observable<Course> {
    return this.http.get<Course>(`/api/lesson/${id}`);
  }

  searchCourses(search: string): Observable<Course[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('search', search);
    return this.http.get<Course[]>(`/api/course`, { params: queryParams });
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>('/api/category');
  }

  deleteCourse(id: number) {
    this.http.delete<Course>(`api/course/${id}`);
  }

  addCourse(data: FormData): Observable<Course> {
    return this.http.post<Course>(`api/course/save`, data);
  }
}
