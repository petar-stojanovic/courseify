import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Course } from './course';

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
    queryParams = queryParams.append("search", search);
    return this.http.get<Course[]>(`/api/course`, {params: queryParams});
  }
}
