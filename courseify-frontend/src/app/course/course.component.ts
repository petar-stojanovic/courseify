import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { Course } from '../interfaces/course';
import { CourseService } from '../services/course.service';
import { Observable, debounceTime, distinct, distinctUntilChanged } from 'rxjs';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { MediaMatcher } from '@angular/cdk/layout';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
})
export class CourseComponent implements OnInit, OnDestroy {
  mobileQuery: MediaQueryList;
  _mobileQueryListener: () => void;

  searchControl = new FormControl();
  listCourses$: Observable<Course[] | undefined> =
    this.courseService.getCourses();

  constructor(
    private courseService: CourseService,
    private router: Router,
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
    this.searchControl.valueChanges
      .pipe(debounceTime(400), distinctUntilChanged())
      .subscribe((value) => {
        this.listCourses$ = this.courseService.searchCourses(value);
      });
    this.getCategories();
  }
  
  ngOnDestroy(): void {
    this.mobileQuery?.removeListener(this._mobileQueryListener);
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
