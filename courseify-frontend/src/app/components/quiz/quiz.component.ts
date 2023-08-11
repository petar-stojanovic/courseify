import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Quiz } from 'src/app/interfaces/Quiz';
import { QuizService } from 'src/app/services/quiz.service';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css'],
})
export class QuizComponent implements OnInit {
  lessonId: string | undefined;
  quiz: Quiz | undefined;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private quizService: QuizService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.lessonId = params['lessonId'];
      if (!this.lessonId) {
        this.router.navigateByUrl('/error/404');
      }
    });
    this.getQuizByLessonId(+this.lessonId!!)
  }

  getQuizByLessonId(id: number) {
    this.quizService.getQuizWithLessonId(id).subscribe((result) => {
      console.log(result);
      
      this.quiz = result;
      console.log(this.quiz);
    });
  }
}
