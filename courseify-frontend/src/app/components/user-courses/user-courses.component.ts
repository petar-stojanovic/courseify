import { Component, OnInit } from '@angular/core';
import { Course } from '../../interfaces/Course';
import { CourseService } from '../../services/course.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/interfaces/User';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-user-courses',
  templateUrl: './user-courses.component.html',
  styleUrls: ['./user-courses.component.css'],
})
export class UserCoursesComponent implements OnInit {
  courses: Course[] = [];
  user: User | null = null;
  previewMode: boolean = false;
  isLoaded = false;

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
          this.courses = result;
          this.previewMode = true;
          this.isLoaded = true;
        });
    } else {
      this.courseService
        .getUserCreatedCourses(this.user!!.id)
        .subscribe((result) => {
          this.courses = result;
          this.isLoaded = true;
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
