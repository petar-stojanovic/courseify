import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Quiz } from 'src/app/interfaces/Quiz';
import { User } from 'src/app/interfaces/User';
import { AuthService } from 'src/app/services/auth.service';
import { QuizService } from 'src/app/services/quiz.service';
import { Location } from '@angular/common';

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
  correctAnswers = 0;
  totalAnswers = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private quizService: QuizService,
    private authService: AuthService,
    private location: Location
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
        if(this.correctAnswers === this.totalAnswers){
          this.completeQuiz(this.quiz!!.id)
        }
        setTimeout(() => {
          this.location.back();
        }, 3000);
      }
    }
  }

  getQuizByLessonId(id: number) {
    this.quizService.getQuizWithLessonId(id).subscribe((result) => {
      this.quiz = result;
      this.totalAnswers = result.questions.length;
      console.log(result);
      this.showNextQuestion();
    });
  }

  onAnswerSelected(index: number) {
    if (this.selectedAnswerIndex != null) {
      return;
    }
    if (this.isCorrectAnswer(index)) {
      this.correctAnswers++;
    }
    this.selectedAnswerIndex = index;

    setTimeout(() => {
      this.showNextQuestion();
    }, 1000);
  }

  isCorrectAnswer(answerId: number): boolean {
    return (
      answerId ===
      this.quiz?.questions[this.currentQuestionIndex].correctAnswerId
    );
  }

  completeQuiz(id: number | undefined) {
    this.quizService.completeQuiz(id, this.user?.id);
  }
}
