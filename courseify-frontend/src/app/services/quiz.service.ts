import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Quiz } from '../interfaces/Quiz';

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor(private http: HttpClient) { }

  getQuizWithLessonId(lessonId: number): Observable<Quiz>{
    return this.http.get<Quiz>(`/api/quiz/${lessonId}`)
  }
}
