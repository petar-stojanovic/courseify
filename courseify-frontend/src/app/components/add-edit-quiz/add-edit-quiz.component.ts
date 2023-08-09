import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-edit-quiz',
  templateUrl: './add-edit-quiz.component.html',
  styleUrls: ['./add-edit-quiz.component.css'],
})
export class AddEditQuizComponent implements OnInit {
  id: string | undefined;

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.id = params['lessonId'];
      if (!this.id) {
        this.router.navigateByUrl('/error/404');
      }
    });
  }
}
