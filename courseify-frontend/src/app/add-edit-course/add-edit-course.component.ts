import { Component, OnInit } from '@angular/core';
import { CourseService } from '../services/course.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../interfaces/course';

@Component({
  selector: 'app-add-edit-course',
  templateUrl: './add-edit-course.component.html',
  styleUrls: ['./add-edit-course.component.css'],
})
export class AddEditCourseComponent implements OnInit {
  id: string | undefined;
  isAddMode: boolean = false;
  course: Course | undefined;

  courseForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    authorId: new FormControl('', [Validators.required]),
    categoryId: new FormControl('', [Validators.required]),
    thumbnail: new FormControl('', [Validators.required]),
    thumbnailSource: new FormControl('', [Validators.required]),
  });

  // editForm = new FormGroup({
  //   title: new FormControl('', [Validators.required]),
  //   description: new FormControl('', [Validators.required]),
  //   authorId: new FormControl('', [Validators.required]),
  //   categoryId: new FormControl('', [Validators.required]),
  //   thumbnail: new FormControl('', [Validators.required]),
  //   thumbnailSource: new FormControl('', [Validators.required]),
  // });

  constructor(
    private courseService: CourseService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.isAddMode = !this.id;

    if (!this.isAddMode && this.id) {
      this.courseService.getCourseById(+this.id).subscribe((result) => {
        this.course = result;
        this.courseForm?.patchValue({
          title: this.course.title,
          description: this.course.description,
          authorId: this.course.author.id.toString(),
          categoryId: this.course.category.id.toString()
        })
      });
    }
  }


  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      // if (this.isAddMode) {
      //   this.addForm?.patchValue({
      //     thumbnailSource: file,
      //   });
      // } else {
      //   this.editForm?.patchValue({
      //     thumbnailSource: file,
      //   });
      // }
      this.courseForm?.patchValue({
        thumbnailSource: file
      })
    }
  }

  submit() {
    const formData = new FormData();
    formData.append('thumbnail', this.courseForm.get('thumbnailSource')?.value!!);
    formData.append('title', this.courseForm.get('title')?.value!!);
    formData.append('description', this.courseForm.get('description')?.value!!);
    formData.append('authorId', this.courseForm.get('authorId')?.value!!);
    formData.append('categoryId', this.courseForm.get('categoryId')?.value!!);

    if (this.isAddMode){
      this.courseService
      .addCourse(formData)
      .subscribe(() => this.router.navigateByUrl('/'));
    }else{
      this.courseService
      .editCourse(this.course!!.id, formData)
      .subscribe(() => this.router.navigateByUrl('/'));
    }

    
  }
  // editCourse(id: number) {
  //   const formData = new FormData();
  //   formData.append('thumbnail', this.editForm.get('thumbnailSource')?.value!!);
  //   formData.append('title', this.editForm.get('title')?.value!!);
  //   formData.append('description', this.editForm.get('description')?.value!!);
  //   formData.append('authorId', this.editForm.get('authorId')?.value!!);
  //   formData.append('categoryId', this.editForm.get('categoryId')?.value!!);

    
  // }
}