import { Component, Input, OnInit } from '@angular/core';
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
  lessonTitle = '';

  currentQuestionIndex = 0;
  selectedAnswerIndex: number | null = null;

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
      this.lessonTitle = params['lessontitle'];
    });
    this.getQuizByLessonId(+this.lessonId!!);
  }

  showNextQuestion() {
    if (this.selectedAnswerIndex != null) {
      this.selectedAnswerIndex = null;
      this.currentQuestionIndex++;

      if (this.currentQuestionIndex >= this.quiz?.questions?.length!!) {
        console.log('Quiz completed');
        return;
      }
    }
  }

  getQuizByLessonId(id: number) {
    this.quizService.getQuizWithLessonId(id).subscribe((result) => {
      this.quiz = result;
      console.log(result);
      this.showNextQuestion();
    });
  }

  onAnswerSelected(index: number) {
    this.selectedAnswerIndex = index;

    this.isCorrectAnswer(index)

    setTimeout(() => {
      this.showNextQuestion();
    }, 2000);
  }

  isCorrectAnswer(index: number) {
    console.log(this.selectedAnswerIndex);
    console.log(
      this.quiz?.questions[this.currentQuestionIndex].correctAnswerId
    );
    return (
      this.selectedAnswerIndex ===
      this.quiz?.questions[this.currentQuestionIndex].correctAnswerId
    );
  }

  completeQuiz(id: number | undefined) {
    this.quizService.completeQuiz(id, this.user?.id);
  }
}
