import { Component } from '@angular/core';
import { Lesson } from '../../interfaces/Lesson';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LessonService } from '../../services/lesson.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-edit-lesson',
  templateUrl: './add-edit-lesson.component.html',
  styleUrls: ['./add-edit-lesson.component.css'],
})
export class AddEditLessonComponent {
  lessonId: string | undefined;
  isAddMode: boolean = false;
  lesson: Lesson | undefined;
  courseId: string | undefined;

  lessonForm = new FormGroup({
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
    courseId: new FormControl('', [Validators.required]),
    file: new FormControl('', [Validators.required]),
    fileSource: new FormControl('', [Validators.required]),
    fileTitle: new FormControl('', [Validators.required]),
    video: new FormControl('', [Validators.required]),
    videoSource: new FormControl('', [Validators.required]),
    videoTitle: new FormControl('', [Validators.required]),
  });

  constructor(
    private lessonService: LessonService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.lessonId = this.route.snapshot.params['lessonId'];
    this.courseId = this.route.snapshot.params['courseId'];
    this.isAddMode = !this.lessonId;

    this.lessonForm?.patchValue({
      courseId: this.courseId,
    });

    if (!this.isAddMode && this.lessonId) {
      this.lessonService.getLessonById(+this.lessonId).subscribe((result) => {
        this.lesson = result;
        this.lessonForm?.patchValue({
          title: this.lesson.title,
          description: this.lesson.description,
          videoTitle: this.lesson.videoTitle,
          fileTitle: this.lesson.fileTitle,
        });
      });
    }
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.lessonForm?.patchValue({
        fileSource: file,
      });
    }
  }

  onVideoChange(event: any) {
    if (event.target.files.length > 0) {
      const video = event.target.files[0];
      this.lessonForm?.patchValue({
        videoSource: video,
      });
    }
  }

  submit() {
    const formData = new FormData();
    formData.append('video', this.lessonForm.get('videoSource')?.value!!);
    formData.append('file', this.lessonForm.get('fileSource')?.value!!);
    formData.append('videoTitle', this.lessonForm.get('videoTitle')?.value!!);
    formData.append('fileTitle', this.lessonForm.get('fileTitle')?.value!!);
    formData.append('title', this.lessonForm.get('title')?.value!!);
    formData.append('description', this.lessonForm.get('description')?.value!!);
    formData.append('quizId', "0");
    formData.append('courseId', this.lessonForm.get('courseId')?.value!!);

    if (this.isAddMode) {
      this.lessonService
        .addLesson(formData)
        .subscribe(() =>
          this.router.navigateByUrl(`/course/${this.courseId}/lessons`)
        );
    } else {
      this.lessonService
        .editLesson(+this.lessonId!!, formData)
        .subscribe(() =>
          this.router.navigateByUrl(`/course/${this.courseId}/lessons`)
        );
    }
  }
}
