import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/interfaces/User';
import { AuthService } from 'src/app/services/auth.service';
import { Course } from '../../interfaces/Course';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-user-courses',
  templateUrl: './user-courses.component.html',
  styleUrls: ['./user-courses.component.css'],
})
export class UserCoursesComponent implements OnInit {
  user: User | null = null;
  previewMode: boolean = false;
  isLoaded = false;
  activeCourses: Course[] = [];
  inactiveCourses: Course[] = [];
  enrolledCourses: Course[] = [];

  ngOnInit(): void {
    this.getUserCourses();
  }

  constructor(
    private courseService: CourseService,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {}

  getUserCourses() {
    this.user = this.authService.getLoggedInUser();
    if (this.router.url.endsWith('learn') && this.user) {
      this.courseService
        .getUserLearnCourses(this.user.id)
        .subscribe((result) => {
          this.enrolledCourses = result;
          this.previewMode = true;
          this.isLoaded = true;
        });
    } else {
      this.courseService
        .getUserCreatedCourses(this.user!!.id)
        .subscribe((result) => {
          this.isLoaded = true;

          result.forEach((course) => {
            if (course.isActive) {
              this.activeCourses.push(course);
            } else {
              this.inactiveCourses.push(course);
            }
          });
        });
    }
  }

  deleteCourse(id: number) {
    this.courseService.deleteCourse(id).subscribe(() => {
      this.reloadPage();
    });
  }

  publishCourse(id: number) {
    this.courseService.publishCourse(id).subscribe(() => this.reloadPage());
  }

  reloadPage() {
    const currentUrl = this.router.url;
    this.router
      .navigateByUrl('/blank', { skipLocationChange: true })
      .then(() => {
        this.router.navigate([currentUrl]);
      });
  }
}
