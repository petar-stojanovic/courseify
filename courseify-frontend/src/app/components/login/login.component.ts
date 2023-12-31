import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormGroupDirective,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ErrorHandleService } from 'src/app/services/errorHandle.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit, OnDestroy {
  loginForm: any;
  fieldRequired = 'This field is required';
  invalidCredentials = false;
  errorMessage: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private errorHandleService: ErrorHandleService
  ) {}

  ngOnInit() {
    this.createForm();
    this.errorHandleService.errorMessage$.subscribe((message) => {
      this.errorMessage = message;
    });
  }

  ngOnDestroy(): void {
    this.errorHandleService.clearErrorMessage();
  }

  createForm() {
    this.loginForm = new FormGroup({
      username: new FormControl(null, [Validators.required]),
      password: new FormControl(null, [Validators.required]),
    });
  }

  checkValidation(input: string) {
    const validation =
      this.loginForm.get(input).invalid &&
      (this.loginForm.get(input).dirty || this.loginForm.get(input).touched);
    return validation;
  }

  onSubmit(formData: FormGroup, loginDirective: FormGroupDirective) {
    const username = formData.value.username;
    const password = formData.value.password;
    this.authService.signInUser(username, password).subscribe(
      (response) => {
        this.errorHandleService.clearErrorMessage();
      },
      (error) => {
        this.invalidCredentials = true;
      }
    );
  }
}
