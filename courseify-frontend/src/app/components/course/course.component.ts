import { MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import jwt_decode from 'jwt-decode';
import { Observable, debounceTime, distinctUntilChanged } from 'rxjs';
import { Course } from '../../interfaces/Course';
import { CourseService } from '../../services/course.service';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
})
export class CourseComponent implements OnInit, OnDestroy {
  mobileQuery: MediaQueryList;
  _mobileQueryListener: () => void;
  searchQuery = '';
  categoryQuery = '';
  user = this.authService.getLoggedInUser();

  searchControl = new FormControl();
  listCourses$: Observable<Course[] | undefined> =
    this.courseService.getCourses();

  constructor(
    private courseService: CourseService,
    private router: Router,
    changeDetectorRef: ChangeDetectorRef,
    media: MediaMatcher,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.searchQuery = params['search'] || null;
      this.categoryQuery = params['category'] || null;
      this.listCourses$ = this.courseService.getCourses(
        this.searchQuery,
        this.categoryQuery
      );
      this.searchControl.setValue(this.searchQuery);
    });

    this.searchControl.valueChanges
      .pipe(debounceTime(400), distinctUntilChanged())
      .subscribe((value) => {
        const category = this.route.snapshot.queryParams['category'];
        if (category) {
          this.router.navigate(['/courses'], {
            queryParams: { search: value, category: category },
          });
        } else {
          this.router.navigate(['/courses'], {
            queryParams: { search: value },
          });
        }
      });
  }

  ngOnDestroy(): void {
    this.mobileQuery?.removeListener(this._mobileQueryListener);
  }

  

  getDecodedAccessToken(): any {
    let token = localStorage.getItem('token');
  }
}
