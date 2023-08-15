import { MediaMatcher } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { Course } from '../../interfaces/Course';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css'],
})
export class CourseComponent implements OnInit {
  mobileQuery: MediaQueryList;
  _mobileQueryListener: () => void;
  searchQuery = '';
  categoryQuery = '';
  user = this.authService.getLoggedInUser();
  isLoaded = false;
  courseList: Course[] = [];
  searchControl = new FormControl();

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
      this.getCourses(this.searchQuery, this.categoryQuery);
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

  getCourses(search?: string, categoryName?: string) {
    this.courseService.getCourses(search, categoryName).subscribe((result) => {
      this.courseList = result;
      this.isLoaded = true;
    });
  }

  getDecodedAccessToken(): any {
    let token = localStorage.getItem('token');
  }
}
