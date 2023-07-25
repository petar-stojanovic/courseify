import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Course } from './course';
import { Observable } from 'rxjs';
import { Lesson } from './lesson';

@Injectable({
  providedIn: 'root',
})
export class LessonService {
  constructor(private http: HttpClient) {}

  getLessonsByCourseId(id: number): Observable<Lesson[]> {
    return this.http.get<Lesson[]>(`/api/lesson/${id}`);
  }

  getVideoByCourseIdAndLessonId(
    videoTitle: string,
    courseId: number,
    lessonId: number
  ): Observable<Blob> {
    return this.http.get(`/api/video`, {
      params: {
        videoTitle: videoTitle,
        courseId: courseId.toString(),
        lessonId: lessonId.toString(),
      },
      responseType: 'blob',
    });
  }
}
