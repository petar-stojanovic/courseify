import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseComponent } from './course/course.component';
import { LessonsComponent } from './lessons/lessons.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { AddEditCourseComponent } from './add-edit-course/add-edit-course.component';


const routes: Routes = [
  { path: '', component: CourseComponent },
  { path: 'courses', component: CourseComponent },
  { path: 'course/add', component: AddEditCourseComponent},
  { path: 'course/:id', component: LessonsComponent },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
