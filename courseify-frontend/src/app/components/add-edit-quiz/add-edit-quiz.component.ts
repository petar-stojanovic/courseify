import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Question } from 'src/app/interfaces/Question';
import { QuizService } from 'src/app/services/quiz.service';

@Component({
  selector: 'app-add-edit-quiz',
  templateUrl: './add-edit-quiz.component.html',
  styleUrls: ['./add-edit-quiz.component.css'],
})
export class AddEditQuizComponent implements OnInit {
  id: string | undefined;
  isAddMode: boolean = false;

  quizForm = this.fb.group({
    questions: this.fb.array([]),
  });

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private quizService: QuizService,
    private fb: FormBuilder,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.id = params['lessonId'];
      if (!this.id) {
        this.router.navigateByUrl('/error/404');
      }
    });

    this.addQuestion();
  }

  get questions() {
    return this.quizForm.controls['questions'] as FormArray;
  }

  addQuestion() {
    const questionForm = this.fb.group({
      question: ['', Validators.required],
      answers: this.fb.array([]),
      correctAnswer: [null, Validators.required],
    });

    this.questions.push(questionForm);
    this.addAnswer(this.questions.length - 1);
    this.addAnswer(this.questions.length - 1);
  }

  deleteQuestion(lessonIndex: number) {
    this.questions.removeAt(lessonIndex);
  }

  addAnswer(questionIndex: number) {
    const answerControl = this.fb.control('', Validators.required);

    (this.questions.at(questionIndex).get('answers') as FormArray).push(
      answerControl
    );
    this.questions.at(questionIndex).get('correctAnswer')?.setValue(null);
  }

  deleteAnswer(questionIndex: number, lessonIndex: number) {
    if (this.questions.at(questionIndex).get('correctAnswer')) {
      this.questions.at(questionIndex).get('correctAnswer')?.setValue(null);
    }

    (this.questions.at(questionIndex).get('answers') as FormArray).removeAt(
      lessonIndex
    );
  }

  submit() {
    if (this.quizForm.valid) {
      const formData = this.quizForm.value;

      const transformedData = {
        lessonId: this.id,
        title: 'Quiz Title',
        questions: formData.questions?.map((question: any) => {
          const transformedQuestion: Question = {
            content: question.question,
            correctAnswerId: question.correctAnswer,
            answers: question.answers,
          };
          return transformedQuestion;
        }),
      };

      this.quizService
        .save(transformedData)
        .subscribe(() => this.location.back());
    }
  }
}
