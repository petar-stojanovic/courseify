import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin, map } from 'rxjs';
import { Quiz } from 'src/app/interfaces/Quiz';
import { AuthService } from 'src/app/services/auth.service';
import { Course } from '../../interfaces/Course';
import { Lesson } from '../../interfaces/Lesson';
import { CourseService } from '../../services/course.service';
import { LessonService } from '../../services/lesson.service';

@Component({
  selector: 'app-lessons',
  templateUrl: './lessons.component.html',
  styleUrls: ['./lessons.component.css'],
})
export class LessonsComponent {
  course: Course | undefined;
  lessons: Lesson[] = [];
  user = this.authService.getLoggedInUser();
  takesCourse = false;
  courseId = Number(this.route.snapshot.paramMap.get('id'));
  quiz: Quiz | undefined;
  progress: number | undefined;
  uncompletedLessonIds: number[] | undefined;
  isLoaded = false;

  constructor(
    private lessonService: LessonService,
    private route: ActivatedRoute,
    private courseService: CourseService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    forkJoin([
      this.courseService.getCourseById(this.courseId),
      this.lessonService.getLessonsByCourseId(this.courseId),
      this.courseService.checkTakesCourse(this.courseId, this.user?.id),
      this.courseService.getProgress(this.courseId, this.user?.id),
    ])
      .pipe(
        map(([course, lessons, userTakesCourse, userProgress]) => {
          return { course, lessons, userTakesCourse, userProgress };
        })
      )
      .subscribe((data) => {
        this.course = data.course;
        this.lessons = data.lessons;
        this.takesCourse = data.userTakesCourse;
        this.progress = data.userProgress.progress * 100;
        this.uncompletedLessonIds = data.userProgress.uncompletedLessonIds;
        this.isLoaded = true;
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
    this.reloadPage();
  }

  reloadPage() {
    const currentUrl = this.router.url;
    this.router
      .navigateByUrl('/blank', { skipLocationChange: true })
      .then(() => {
        this.router.navigate([currentUrl]);
      });
  }

  downloadCertificate() {
    this.lessonService
      .downloadCertificate(this.courseId, this.user?.id)
      .subscribe((res) => {
        const fileURL = URL.createObjectURL(res);
        window.open(fileURL, '_blank');
      });
  }
}
