import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
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
    return this.http.get(`/api/lesson/${lessonId}/video`, {
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

  downloadPDF(id: number): Observable<Blob> {
    const options = { responseType: 'blob' as 'json' };
    return this.http
      .get<Blob>(`/api/lesson/${id}/file`, options)
      .pipe(map((res) => new Blob([res], { type: 'application/pdf' })));
  }

  downloadCertificate(courseId: number, userId: number| undefined): Observable<Blob> {
    const options = { responseType: 'blob' as 'json' };
    const body = {
      courseId: courseId,
      userId: userId,
    };
    return this.http
      .post<Blob>(`/api/course/generate-pdf`, body, options)
      .pipe(map((res) => new Blob([res], { type: 'application/pdf' })));
  }
}
