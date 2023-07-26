import { Component } from '@angular/core';
import { CourseService } from '../services/course.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { courseRequest } from '../interfaces/courseRequest';

@Component({
  selector: 'app-add-edit-course',
  templateUrl: './add-edit-course.component.html',
  styleUrls: ['./add-edit-course.component.css'],
})
export class AddEditCourseComponent {
  courseForm: FormGroup;
  message: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private courseService: CourseService
  ) {
    this.courseForm = this.formBuilder.group({
      id: ['', Validators.required],
      title: ['', Validators.required],
      description: ['', Validators.required],
      authorId: [null, Validators.required],
      categoryId: [null, Validators.required],
      thumbnail: [null, Validators.required], // For the file, we initialize it with null
    });
  }

  onSubmit() {
    // if (this.courseForm.invalid) {
    //   return;
    // }

    const courseRequest: courseRequest = {
      title: this.courseForm.value.title,
      thumbnail: this.courseForm.value.thumbnail,
      description: this.courseForm.value.description,
      authorId: this.courseForm.value.authorId,
      categoryId: this.courseForm.value.categoryId,
    };

    this.courseService.addCourse(courseRequest).subscribe(
      (response: any) => {
        console.log('Course added successfully:', response);
        this.message = 'Course added successfully!';
      },
      (error: any) => {
        console.error('Error adding course:', error);
        this.message = 'Error adding course. Please try again.';
      }
    );
  }

  onThumbnailSelected(input: HTMLInputElement) {
    if (input.files && input.files.length > 0) {
      this.courseForm.patchValue({
        thumbnail: input.files[0]
      });
    }
  }
}
