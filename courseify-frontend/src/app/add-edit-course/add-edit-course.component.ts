import { Component } from '@angular/core';
import { CourseService } from '../services/course.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { courseRequest } from '../interfaces/courseRequest';

@Component({
  selector: 'app-add-edit-course',
  templateUrl: './add-edit-course.component.html',
  styleUrls: ['./add-edit-course.component.css'],
})
export class AddEditCourseComponent {
  myForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    authorId: new FormControl('', [Validators.required]),
    categoryId: new FormControl('', [Validators.required]),
    thumbnail: new FormControl('', [Validators.required]),
    thumbnailSource: new FormControl('', [Validators.required]),
  })

  constructor(private courseService: CourseService){}

  get f(){
    return this.myForm.controls;
  }

  onFileChange(event: any){
    if (event.target.files.length > 0){
      const file = event.target.files[0];
      this.myForm.patchValue({
        thumbnailSource: file
      });
    }
  }

  submit(){
    const formData = new FormData();
    formData.append('file', this.myForm.get('fileSource')?.value!!);
   
    this.courseService.addCourse(formData).subscribe(
      result => {
        console.log(result);
      }
    )
  }
}
