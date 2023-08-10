import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-edit-quiz',
  templateUrl: './add-edit-quiz.component.html',
  styleUrls: ['./add-edit-quiz.component.css'],
})
export class AddEditQuizComponent implements OnInit {
  lessonId: string | undefined;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.lessonId = params['lessonId'];
      if (!this.lessonId) {
        this.router.navigateByUrl('/error/404');
      }
    });
  }
}
