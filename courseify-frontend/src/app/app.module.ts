import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatRadioModule } from '@angular/material/radio';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VgBufferingModule } from '@videogular/ngx-videogular/buffering';
import { VgControlsModule } from '@videogular/ngx-videogular/controls';
import { VgCoreModule } from '@videogular/ngx-videogular/core';
import { VgOverlayPlayModule } from '@videogular/ngx-videogular/overlay-play';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddEditCourseComponent } from './components/add-edit-course/add-edit-course.component';
import { AddEditLessonComponent } from './components/add-edit-lesson/add-edit-lesson.component';
import { CategoriesComponent } from './components/categories/categories.component';
import { CourseComponent } from './components/course/course.component';
import { ErrorComponent } from './components/error/error.component';
import { HeaderComponent } from './components/header/header.component';
import { LessonsComponent } from './components/lessons/lessons.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { ThumbnailComponent } from './components/thumbnail/thumbnail.component';
import { UserCoursesComponent } from './components/user-courses/user-courses.component';
import { UserDetailsComponent } from './components/user-details/user-details.component';
import { VideoComponent } from './components/video/video.component';
import { AuthInterceptor } from './interceptors/AuthInterceptor';
import { HttpResponseInterceptor } from './interceptors/HttpResponseInterceptor';
import { QuizComponent } from './components/quiz/quiz.component';
import { AddEditQuizComponent } from './components/add-edit-quiz/add-edit-quiz.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { FooterComponent } from './components/footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    CourseComponent,
    LessonsComponent,
    VideoComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    AddEditCourseComponent,
    CategoriesComponent,
    AddEditLessonComponent,
    ThumbnailComponent,
    UserDetailsComponent,
    UserCoursesComponent,
    ErrorComponent,
    QuizComponent,
    AddEditQuizComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatSlideToggleModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    MatExpansionModule,
    BrowserAnimationsModule,
    MatCardModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatSidenavModule,
    MatListModule,
    MatFormFieldModule,
    MatChipsModule,
    MatIconModule,
    MatAutocompleteModule,
    MatMenuModule,
    MatRadioModule,
    MatProgressBarModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpResponseInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
