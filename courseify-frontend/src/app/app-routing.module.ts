import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddEditCourseComponent } from './components/add-edit-course/add-edit-course.component';
import { CourseComponent } from './components/course/course.component';
import { LessonsComponent } from './components/lessons/lessons.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AddEditLessonComponent } from './components/add-edit-lesson/add-edit-lesson.component';
import { VideoComponent } from './components/video/video.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { UserCoursesComponent } from './components/user-courses/user-courses.component';
import { ForbiddenComponent } from './components/forbidden/forbidden.component';

const routes: Routes = [
  { path: '', component: CourseComponent },
  { path: 'courses', component: CourseComponent },
  { path: 'course/add', component: AddEditCourseComponent },
  { path: 'course/:id/lessons', component: LessonsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'course/:id/edit', component: AddEditCourseComponent },
  {
    path: 'course/:courseId/lessons/:lessonId/edit',
    component: AddEditLessonComponent,
  },
  { path: 'course/:courseId/lessons/add', component: AddEditLessonComponent },
  {
    path: 'course/:courseId/lessons/:lessonId/video',
    component: VideoComponent,
  },
  { path: 'user/:id', component: UserDetailsComponent },
  { path: 'user/:id/learn', component: UserCoursesComponent },
  { path: 'user/:id/created', component: UserCoursesComponent },
  { path: 'forbidden/:code', component: ForbiddenComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
