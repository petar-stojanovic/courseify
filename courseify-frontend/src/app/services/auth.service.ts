import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthResponse } from '../authResponse';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  username?: string;

  constructor(private router: Router, private http: HttpClient) {}

  registerUser(
    firstName: string,
    lastName: string,
    username: string,
    email: string,
    password: string,
    confirmPassword: string
  ) {
    const body = {
      firstName,
      lastName,
      username,
      email,
      password,
      confirmPassword,
      role: 'USER',
    };
    return this.http.post<AuthResponse>(`/api/auth/register`, body);
  }

  signinUser(username: string, password: string) {
    const body = {
      username: username,
      password: password,
    };
    return this.http.post<AuthResponse>(`/api/auth/authenticate`, body);
  }

  logout() {
    return this.http.post(`/api/auth/logout`, {});
  }

  isAunthenticated() {
    return localStorage.getItem('token') != null;
  }

  // getUserDetails() {
  //   return this.username;
  // }
}
