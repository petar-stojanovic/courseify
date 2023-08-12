import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Quiz } from 'src/app/interfaces/Quiz';
import { User } from 'src/app/interfaces/User';
import { AuthService } from 'src/app/services/auth.service';
import { QuizService } from 'src/app/services/quiz.service';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css'],
})
export class QuizComponent implements OnInit {
  lessonId: string | undefined;
  quiz: Quiz | undefined;
  user = this.authService.getLoggedInUser();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private quizService: QuizService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.lessonId = params['lessonId'];
      if (!this.lessonId) {
        this.router.navigateByUrl('/error/404');
      }
    });
    this.getQuizByLessonId(+this.lessonId!!);
  }

  getQuizByLessonId(id: number) {
    this.quizService.getQuizWithLessonId(id).subscribe((result) => {
      this.quiz = result;
      console.log(result)
    });
  }

  completeQuiz(id: number | undefined){
    this.quizService.completeQuiz(id, this.user?.id)
  }
}
