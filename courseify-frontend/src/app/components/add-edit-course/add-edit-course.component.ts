import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../../interfaces/Category';
import { CategoryService } from '../../services/category.service';
import { CourseService } from '../../services/course.service';
import { Course } from 'src/app/interfaces/Course';
import { mergeMap } from 'rxjs';

@Component({
  selector: 'app-add-edit-course',
  templateUrl: './add-edit-course.component.html',
  styleUrls: ['./add-edit-course.component.css'],
})
export class AddEditCourseComponent implements OnInit {
  id: string | undefined;
  isAddMode: boolean = false;
  course: Course | undefined;
  categoryList: Category[] = [];
  isLoaded = false;

  allCategories: Category[] = [];
  filteredCategories: Category[] = [];
  @ViewChild('categoryInput') categoryInput:
    | ElementRef<HTMLInputElement>
    | undefined;

  readonly separatorKeysCodes = [ENTER, COMMA] as const;

  courseForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    categoryIds: new FormControl('', [Validators.required]),
    thumbnail: new FormControl('', [Validators.required]),
    thumbnailSource: new FormControl('', [Validators.required]),
  });

  constructor(
    private courseService: CourseService,
    private router: Router,
    private route: ActivatedRoute,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.isAddMode = !this.id;
    this.getAllCategories();

    if(this.isAddMode){
      this.isLoaded = true;
    }

    if (!this.isAddMode && this.id) {
      this.courseService
        .getCourseById(+this.id)
        .pipe(
          mergeMap((result) => {
            this.course = result;
            this.courseForm?.patchValue({
              title: this.course.title,
              description: this.course.description,
            });
            return this.categoryService.getCategoriesForCourse(+this.id!!);
          })
        )
        .subscribe((res) => {
          this.isLoaded = true;
          this.categoryList = res;
          const categoryListOfString = this.categoryList
          .map((category) => category.id)
          .join(', ');
          this.courseForm.get("categoryIds")?.setValue(categoryListOfString) 
        });
    }

    this.courseForm
      .get('categoryIds')
      ?.valueChanges.subscribe((res: string | null) => {
        this.filteredCategories = this._filter(res!!);
      });
  }

  getAllCategories() {
    this.categoryService
      .getAllCategories()
      .subscribe((res) => (this.allCategories = res));
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.courseForm?.patchValue({
        thumbnailSource: file,
      });
    }
  }

  submit() {
    const formData = new FormData();
    formData.append(
      'thumbnail',
      this.courseForm.get('thumbnailSource')?.value!!
    );
    formData.append('title', this.courseForm.get('title')?.value!!);
    formData.append('description', this.courseForm.get('description')?.value!!);
    const categoryListOfString = this.categoryList
      .map((category) => category.id)
      .join(', ');
    formData.append('categoryIds', categoryListOfString);

    if (this.isAddMode) {
      this.courseService
        .addCourse(formData)
        .subscribe(() => this.router.navigateByUrl('user/created'));
    } else {
      this.courseService
        .editCourse(this.course!!.id, formData)
        .subscribe(() => this.router.navigateByUrl('user/created'));
    }
  }

  remove(category: Category): void {
    const index = this.categoryList.indexOf(category);

    if (index >= 0) {
      this.categoryList.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.categoryList.push({
      id: event.option.value,
      name: event.option.viewValue,
    });

    this.categoryInput!!.nativeElement.value = '';
  }

  _filter(value: string): Category[] {
    if (value) {
      let filterValue = value.toString().toLowerCase();

      return this.allCategories.filter((category) =>
        category.name.toLowerCase().includes(filterValue)
      );
    }
    return this.allCategories;
  }
}
