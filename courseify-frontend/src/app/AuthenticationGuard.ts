import { Injectable } from '@angular/core';
import {
  Router,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationGuard {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate():
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (this.authService.isAuthenticated()) {
      return true;
    } else {
      this.router.navigateByUrl('/login');
      return false;
    }
  }
}
