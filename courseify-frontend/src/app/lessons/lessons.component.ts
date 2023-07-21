import { Component } from '@angular/core';
import { Lesson } from '../lesson';
import { Observable } from 'rxjs';
import { LessonService } from '../lesson.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-lessons',
  templateUrl: './lessons.component.html',
  styleUrls: ['./lessons.component.css'],
})
export class LessonsComponent {
  lessons: Lesson[] | undefined;

  constructor(
    private lessonService: LessonService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getLessonsByCourseId();
  }

  getLessonsByCourseId(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.lessonService
      .getLessonsByCourseId(id)
      .subscribe((lesson) => console.log(lesson));
  }
}