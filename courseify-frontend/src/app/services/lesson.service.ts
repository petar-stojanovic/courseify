import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Course } from '../interfaces/Course';
import { Observable } from 'rxjs';
import { Lesson } from '../interfaces/Lesson';

@Injectable({
  providedIn: 'root',
})
export class LessonService {
  constructor(private http: HttpClient) {}

  getLessonsByCourseId(id: number): Observable<Lesson[]> {
    return this.http.get<Lesson[]>(`/api/course/${id}/lessons`);
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

  getVideoByLessonId(lessonId: number): Observable<Blob> {
    return this.http.get(`/api/video`, {
      params: {
        lessonId,
      },
      responseType: 'blob',
    });
  }

  getLessonById(id: number): Observable<Lesson> {
    return this.http.get<Lesson>(`/api/lesson/${id}`);
  }

  addLesson(data: FormData): Observable<Lesson> {
    return this.http.post<Lesson>(`/api/lesson/save`, data);
  }

  editLesson(id: number, data: FormData): Observable<Lesson> {
    return this.http.put<Lesson>(`/api/lesson/${id}`, data);
  }

  deleteLesson(id: number) {
    return this.http.delete<Lesson>(`/api/lesson/${id}`);
  }
}
