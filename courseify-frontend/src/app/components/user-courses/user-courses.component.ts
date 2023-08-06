import { Component, OnInit } from '@angular/core';
import { Course } from '../../interfaces/Course';
import { CourseService } from '../../services/course.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-user-courses',
  templateUrl: './user-courses.component.html',
  styleUrls: ['./user-courses.component.css'],
})
export class UserCoursesComponent implements OnInit {
  courses: Course[] = [];

  ngOnInit(): void {
    this.getUserLearnCourses();
  }

  constructor(
    private courseService: CourseService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  getUserLearnCourses() {
    const id = this.route.snapshot.params['id'];
    if (this.router.url.endsWith('learn')) {
      this.courseService
        .getUserLearnCourses(id)
        .subscribe((result) => (this.courses = result));
    } else {
      this.courseService
        .getUserCreatedCourses(id)
        .subscribe((result) => (this.courses = result));
    }
  }

  deleteCourse(id: number) {
    console.log('delete Course called');

    this.courseService.deleteCourse(id).subscribe(() => {
      this.getUserLearnCourses();
    });
  }
}
