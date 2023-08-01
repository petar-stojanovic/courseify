import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap, mergeMap } from 'rxjs';
import { AuthService } from './auth.service';
import { Course } from '../interfaces/Course';
import { Category } from '../interfaces/Category';

@Injectable({
  providedIn: 'root',
})
export class CourseService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  getCourses(search?: string, categoryName?: string): Observable<Course[]> {
    let queryParams = new HttpParams();
    if (search && categoryName) {
      queryParams = queryParams.append('search', search);
      queryParams = queryParams.append('categoryName', categoryName);
    } else if (search) {
      queryParams = queryParams.append('search', search);
    } else if (categoryName) {
      queryParams = queryParams.append('categoryName', categoryName);
    }

    return this.http.get<Course[]>(`/api/course`, { params: queryParams });
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

  editCourse(id: number, data: FormData): Observable<Course> {
    return this.http.put<Course>(`api/course/${id}`, data);
  }

  getCourseById(id: number): Observable<Course> {
    return this.http.get<Course>(`api/course/${id}`);
  }

  getThumbnail(id: number): Observable<Blob> {
    return this.http.get(`/api/course/${id}/thumbnail`, {
      params: {
        id,
      },
      responseType: 'blob',
    });
  }

  enrollUserToCourse(courseId: number){
    if (this.authService.isAunthenticated()) {
      const token = localStorage.getItem('token');
      let body = null;

      this.authService
        .getUserByToken()
        .pipe(
          mergeMap((result) =>
            this.http.post(`/api/course/enroll`, {
              courseId: courseId,
              userId: result.id,
            })
          )
        ).subscribe(() => console.log("Successfully enrolled user")
        );
    }
  }

  getUserLearnCourses(id: number): Observable<Course[]>{
    return this.http.get<Course[]>(`/api/user/${id}/learn`)
  }

  getUserCreatedCourses(id: number): Observable<Course[]>{
    return this.http.get<Course[]>(`/api/user/${id}/created`)
  }
}
