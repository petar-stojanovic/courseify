import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, tap } from 'rxjs';
import { AuthResponse } from '../interfaces/AuthResponse';
import { User } from '../interfaces/User';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private cachedUser: User | null = null;

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

  signInUser(username: string, password: string) {
    const body = {
      username: username,
      password: password,
    };
    return this.http.post<AuthResponse>(`/api/auth/authenticate`, body).pipe(
      tap((response) => {
        localStorage.setItem('token', response['access_token']);
        this.getUserByToken().subscribe((user) => {
          this.cachedUser = user;
          localStorage.setItem('user_data', JSON.stringify(user));
          location.href = '/';
        });
      })
    );
  }

  logout() {
    localStorage.removeItem('token');
    this.clearCachedUser();
    return this.http.post(`/api/auth/logout`, {});
  }

  isAuthenticated() {
    return (
      localStorage.getItem('token') != null &&
      localStorage.getItem('user_data') != null
    );
  }

  getUserByToken(): Observable<User> {
    if (this.cachedUser) {
      return of(this.cachedUser);
    } else {
      const token = localStorage.getItem('token');
      return this.http.get<User>(`/api/auth/user/${token}`);
    }
  }

  loadCachedUser(): void {
    const userData = localStorage.getItem('user_data');
    if (userData) {
      this.cachedUser = JSON.parse(userData);
    }
  }

  clearCachedUser(): void {
    this.cachedUser = null;
    localStorage.removeItem('user_data');
  }

  getLoggedInUser(): User | null {
    return this.cachedUser;
  }

  getToken() {
    return localStorage.getItem('token');
  }
}
