import { Component, Input } from '@angular/core';
import { Lesson } from '../../interfaces/Lesson';
import { LessonService } from '../../services/lesson.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../../interfaces/Course';
import { CourseService } from '../../services/course.service';
import { AuthService } from 'src/app/services/auth.service';
import { Quiz } from 'src/app/interfaces/Quiz';
import { QuizService } from 'src/app/services/quiz.service';


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
  quiz: Quiz | undefined;

  constructor(
    private lessonService: LessonService,
    private route: ActivatedRoute,
    private courseService: CourseService,
    private authService: AuthService,
    private router: Router,
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
        console.log(lessons.at(1));
        
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

  checkEnrolledStudent() {
    this.courseService
      .checkTakesCourse(this.courseId, this.user?.id)
      .subscribe(result => {
        console.log(result);
        this.takesCourse = result;
      });
  }

  reloadPage() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/blank', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}
