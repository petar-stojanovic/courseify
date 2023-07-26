import { Component, OnInit } from '@angular/core';
import { Course } from '../interfaces/course';
import { CourseService } from '../services/course.service';
import { Observable, debounceTime, distinct, distinctUntilChanged } from 'rxjs';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
})
export class CourseComponent implements OnInit {
  searchControl = new FormControl();
  listCourses$: Observable<Course[] | undefined> =
    this.courseService.getCourses();

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    this.searchControl.valueChanges
      .pipe(debounceTime(400), distinctUntilChanged())
      .subscribe((value) => {
        this.listCourses$ = this.courseService.searchCourses(value);
      });
  }

  deleteCourse(id: number) {
    this.courseService.deleteCourse(id);
  }

  editCourse(id: number) {}
}
