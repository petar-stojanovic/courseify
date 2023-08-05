import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormGroupDirective,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm: any;
  fieldRequired = 'This field is required';
  invalidCredentials = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.createForm();
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
        location.href = '/';
      },
      (error) => {
        this.invalidCredentials = true;
      }
    );
  }
}
