import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { VgApiService } from '@videogular/ngx-videogular/core';
import { Lesson } from '../../interfaces/Lesson';
import { LessonService } from '../../services/lesson.service';

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css'],
})
export class VideoComponent implements OnInit {
  videoUrl?: SafeUrl | null = null;
  lesson: Lesson | undefined;

  constructor(
    private lessonService: LessonService,
    private sanitizer: DomSanitizer,
    private route: ActivatedRoute,
    private api: VgApiService
  ) {}

  ngOnInit(): void {
    this.getVideo();
  }

  getVideo(): void {
    const lessonId = this.route.snapshot.params['lessonId'];
    this.lessonService.getLessonById(+lessonId).subscribe((result) => {
      this.lesson = result;
    });

    this.lessonService.getVideoByLessonId(lessonId).subscribe(
      (response: any) => {
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

  onPlayerReady(api: VgApiService) {
    this.api = api;
    this.api.getDefaultMedia().subscriptions.ended.subscribe(() => {
      // TODO: this.onVideoEnded();
    });
    this.api
      .getDefaultMedia()
      .subscriptions.loadedMetadata.subscribe(($event) => {
        //TODO: Math.floor(this.api.duration);
      });
  }
}
