import { Component, OnInit } from '@angular/core';
import { Course } from '../course';
import { CourseService } from '../course.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
})
export class CourseComponent implements OnInit {
  listCourses$: Observable<Course[] | undefined> =
    this.courseService.getCourses();

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {}
}
