import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthResponse } from './authResponse';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  token?: string;
  username?: string;

  constructor(private router: Router, private http: HttpClient) {}

  registerUser(email: string, password: string, username: string) {
    const body = {
      firstName: 'asdas',
      lastName: 'asdasdas',
      email: email,
      password: password,
      username: username,
      role: 'USER',
    };
    return this.http
      .post<AuthResponse>(`/api/auth/register`, body)
      .subscribe((response) => {
        localStorage.setItem('token', response['access_token']);
        this.router.navigateByUrl('');
      });
  }

  signinUser(username: string, password: string) {
    const body = {
      username: username,
      password: password,
    };
    return this.http
      .post<AuthResponse>(`/api/auth/authenticate`, body)
      .subscribe((response) => {
        localStorage.setItem('token', response['access_token']);
        this.router.navigateByUrl('');
      });
  }


  logout() {
    return this.http
      .post(`/api/auth/logout`,{})
      .subscribe(() => {
        localStorage.removeItem('token');
        this.router.navigateByUrl('');
      });
  }

  // getToken() {
  //   return this.token;
  // }

  // isAunthenticated() {
  //   return this.token != null;
  // }

  // getUserDetails() {
  //   return this.username;
  // }
}
