import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddEditCourseComponent } from './add-edit-course/add-edit-course.component';
import { CourseComponent } from './course/course.component';
import { LessonsComponent } from './lessons/lessons.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AddEditLessonComponent } from './add-edit-lesson/add-edit-lesson.component';
import { VideoComponent } from './video/video.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { UserCoursesComponent } from './user-courses/user-courses.component';

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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
