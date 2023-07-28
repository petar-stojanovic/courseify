import { Component, Input, OnInit } from '@angular/core';
import { LessonService } from '../services/lesson.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { VgApiService } from '@videogular/ngx-videogular/core';
import { ActivatedRoute } from '@angular/router';
import { Lesson } from '../interfaces/lesson';

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css'],
})
export class VideoComponent implements OnInit {
  // @Input() videoTitle = '';
  // @Input() courseId = 0;
  // @Input() lessonId = 0;

  videoUrl?: SafeUrl | null = null;
  lesson: Lesson | undefined;

  constructor(
    private lessonService: LessonService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getVideo();
  }

  getVideo(): void {
    const lessonId = this.route.snapshot.params['lessonId'];
    this.lessonService.getLessonById(+lessonId).subscribe((result) => {
      this.lesson = result;
      console.log(this.lesson);
    });

    this.lessonService
      .getVideoByLessonId(
        lessonId
      )
      .subscribe(
        (response: any) => {
          console.log(response);
          const blob = new Blob([response], { type: 'video/mp4' });
          this.videoUrl = this.sanitizer.bypassSecurityTrustUrl(
            URL.createObjectURL(blob)
          );
        },
        (error) => {
          console.error('Error loading video:', error);
        }
      );

    // this.lessonService
    //   .getVideoByCourseIdAndLessonId(
    //     this.lesson!!.videoTitle,
    //     this.lesson!!.course.id,
    //     this.lesson!!.id
    //   )
    //   .subscribe(
    //     (response: any) => {
    //       console.log(response);
    //       const blob = new Blob([response], { type: 'video/mp4' });
    //       this.videoUrl = this.sanitizer.bypassSecurityTrustUrl(
    //         URL.createObjectURL(blob)
    //       );
    //     },
    //     (error) => {
    //       console.error('Error loading video:', error);
    //     }
    //   );
  }
}
