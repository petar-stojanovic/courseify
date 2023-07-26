import { Component, Input, OnInit } from '@angular/core';
import { LessonService } from '../services/lesson.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { VgApiService } from '@videogular/ngx-videogular/core';

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css'],
})
export class VideoComponent implements OnInit {
  @Input() videoTitle = '';
  @Input() courseId = 0;
  @Input() lessonId = 0;

  videoUrl?: SafeUrl | null = null;

  constructor(
    private lessonService: LessonService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.getVideo();
  }

  getVideo(): void {
    this.lessonService
      .getVideoByCourseIdAndLessonId(
        this.videoTitle,
        this.courseId,
        this.lessonId
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
  }
}
