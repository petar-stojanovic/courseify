import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import {
  MatChipsModule
} from '@angular/material/chips';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { VgBufferingModule } from '@videogular/ngx-videogular/buffering';
import { VgControlsModule } from '@videogular/ngx-videogular/controls';
import { VgCoreModule } from '@videogular/ngx-videogular/core';
import { VgOverlayPlayModule } from '@videogular/ngx-videogular/overlay-play';
import { AuthInterceptor } from './AuthInterceptor';
import { AddEditCourseComponent } from './add-edit-course/add-edit-course.component';
import { AddEditLessonComponent } from './add-edit-lesson/add-edit-lesson.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CategoriesComponent } from './categories/categories.component';
import { CourseComponent } from './course/course.component';
import { HeaderComponent } from './header/header.component';
import { LessonsComponent } from './lessons/lessons.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ThumbnailComponent } from './thumbnail/thumbnail.component';
import { UserCoursesComponent } from './user-courses/user-courses.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { VideoComponent } from './video/video.component';

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
    MatMenuModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
