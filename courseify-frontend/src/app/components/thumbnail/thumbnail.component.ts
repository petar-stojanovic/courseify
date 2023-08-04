import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from '../../services/course.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-thumbnail',
  templateUrl: './thumbnail.component.html',
  styleUrls: ['./thumbnail.component.css'],
})
export class ThumbnailComponent implements OnInit {
  @Input()
  courseId = 0;
  thumbnailUrl?: SafeUrl | null = null;

  constructor(
    private courseService: CourseService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.getThumbnail();
  }

  getThumbnail(): void {
    this.courseService.getThumbnail(this.courseId).subscribe(
      (response: any) => {
        const blob = new Blob([response], { type: 'image/jpeg' });
        this.thumbnailUrl = this.sanitizer.bypassSecurityTrustUrl(
          URL.createObjectURL(blob)
        );
      },
      (error) => {
        console.error('Error loading thumbnail:', error);
      }
    );
  }
}
