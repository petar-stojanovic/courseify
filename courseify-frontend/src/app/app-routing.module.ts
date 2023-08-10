import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from './AuthenticationGuard';
import { AddEditCourseComponent } from './components/add-edit-course/add-edit-course.component';
import { AddEditLessonComponent } from './components/add-edit-lesson/add-edit-lesson.component';
import { CourseComponent } from './components/course/course.component';
import { ErrorComponent } from './components/error/error.component';
import { LessonsComponent } from './components/lessons/lessons.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { UserCoursesComponent } from './components/user-courses/user-courses.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { VideoComponent } from './components/video/video.component';
import { QuizComponent } from './components/quiz/quiz.component';
import { AddEditQuizComponent } from './components/add-edit-quiz/add-edit-quiz.component';
import { AuthorizationGuard } from './AuthorizationGuard';

const routes: Routes = [
  { path: '', component: CourseComponent },
  { path: 'courses', component: CourseComponent },
  {
    path: 'course/add',
    component: AddEditCourseComponent,
    canActivate: [AuthenticationGuard],
  },
  { path: 'course/:id/lessons', component: LessonsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'course/:id/edit',
    component: AddEditCourseComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'course/:courseId/lessons/:lessonId/edit',
    component: AddEditLessonComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'course/:courseId/lessons/add',
    component: AddEditLessonComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'course/:courseId/lessons/:lessonId/video',
    component: VideoComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'user',
    component: UserDetailsComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'user/learn',
    component: UserCoursesComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'user/created',
    component: UserCoursesComponent,
    canActivate: [AuthenticationGuard],
  },

  {
    path: 'quiz',
    component: QuizComponent,
    canActivate: [AuthenticationGuard],
  },
  {
    path: 'quiz/add',
    component: AddEditQuizComponent,
    canActivate: [AuthenticationGuard,AuthorizationGuard],
  },
  {
    path: 'course/:courseId/lessons/:lessonId/quiz',
    component: QuizComponent,
    canActivate: [AuthenticationGuard],
  },
  { path: 'error/:code', component: ErrorComponent },
  { path: '**', component: ErrorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
