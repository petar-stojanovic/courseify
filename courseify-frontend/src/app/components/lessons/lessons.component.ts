import { Component, Input } from '@angular/core';
import { Lesson } from '../../interfaces/Lesson';
import { LessonService } from '../../services/lesson.service';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../../interfaces/Course';
import { CourseService } from '../../services/course.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-lessons',
  templateUrl: './lessons.component.html',
  styleUrls: ['./lessons.component.css'],
})
export class LessonsComponent {
  course: Course | undefined;
  lessons: Lesson[] = [];
  panelOpenState = false;
  user = this.authService.getLoggedInUser();
  takesCourse = false;
  courseId = Number(this.route.snapshot.paramMap.get('id'));

  constructor(
    private lessonService: LessonService,
    private route: ActivatedRoute,
    private courseService: CourseService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.getLessonsByCourseId();
    this.checkEnrolledStudent();
  }

  getLessonsByCourseId(): void {
    this.courseService.getCourseById(this.courseId).subscribe((result) => {
      this.course = result;
    });
    this.lessonService
      .getLessonsByCourseId(this.courseId)
      .subscribe((lessons) => {
        this.lessons = lessons;
      });
  }

  deleteLesson(id: number) {
    this.lessonService.deleteLesson(id).subscribe();
  }

  openFile(id: number) {
    this.lessonService.downloadPDF(id).subscribe((res) => {
      const fileURL = URL.createObjectURL(res);
      window.open(fileURL, '_blank');
    });
  }

  enrollStudent(courseId: number) {
    this.courseService.enrollUserToCourse(courseId);
  }

  checkEnrolledStudent() {
    this.courseService
      .checkTakesCourse(this.courseId, this.user?.id)
      .subscribe(result => {
        console.log(result);
        this.takesCourse = result;
      });
  }
}
