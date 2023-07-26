import { Component } from '@angular/core';
import { Lesson } from '../interfaces/lesson';
import { Observable } from 'rxjs';
import { LessonService } from '../services/lesson.service';
import { ActivatedRoute } from '@angular/router';
import { MatExpansionModule } from '@angular/material/expansion';

@Component({
  selector: 'app-lessons',
  templateUrl: './lessons.component.html',
  styleUrls: ['./lessons.component.css'],
})
export class LessonsComponent {
  courseId?: number;
  lessons: Lesson[] = [];
  panelOpenState = false;

  constructor(
    private lessonService: LessonService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getLessonsByCourseId();
  }

  getLessonsByCourseId(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.lessonService.getLessonsByCourseId(id).subscribe((lessons) => {
      this.lessons = lessons;
    });
  }
}
