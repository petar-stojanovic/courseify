import { Component, OnInit } from '@angular/core';
import { Course } from '../interfaces/course';
import { CourseService } from '../services/course.service';
import { Observable, debounceTime, distinct, distinctUntilChanged } from 'rxjs';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
})
export class CourseComponent implements OnInit {
  searchControl = new FormControl();
  listCourses$: Observable<Course[] | undefined> =
    this.courseService.getCourses();

  constructor(private courseService: CourseService, private router: Router) {}

  ngOnInit(): void {
    this.searchControl.valueChanges
      .pipe(debounceTime(400), distinctUntilChanged())
      .subscribe((value) => {
        this.listCourses$ = this.courseService.searchCourses(value);
      });
    this.getCategories();
  }

  getCategories() {
    return this.courseService
      .getCategories()
      .subscribe((res) => console.log(res));
  }

  deleteCourse(id: number) {
    console.log('delete Course called');

    this.courseService.deleteCourse(id).subscribe(() => {
      this.listCourses$ = this.courseService.getCourses();
    });
  }
}
