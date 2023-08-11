import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Quiz } from '../interfaces/Quiz';

@Injectable({
  providedIn: 'root',
})
export class QuizService {
  constructor(private http: HttpClient) {}

  getQuizWithLessonId(id: number): Observable<Quiz> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('id', id);
    return this.http.get<Quiz>('/api/quiz', { params: queryParams });
  }

  save(data: any): Observable<Quiz> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.http.post<Quiz>(`/api/quiz`, data, httpOptions);
  }
}
