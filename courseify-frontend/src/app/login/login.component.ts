import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective } from '@angular/forms';
import { AuthService } from '../auth.service';
import {  Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm: any;
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.createForm();
  }

  createForm() {
    this.loginForm = new FormGroup({
      username: new FormControl(null),
      password: new FormControl(null),
    });
  }
  onSubmit(formData: FormGroup, loginDirective: FormGroupDirective) { 
    const username = formData.value.username;
    const password = formData.value.password;
    this.authService.signinUser(username, password)
    // .subscribe((res) => {
    //   let jwtToken = res['access_token'];
    //   localStorage.setItem('token', jwtToken);
    //   console.log(localStorage);
    //   this.router.navigateByUrl("")
    // });
  }
}
