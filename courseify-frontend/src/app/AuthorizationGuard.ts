import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, UrlTree } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthorizationGuard {
  userHasAccess = false;

  constructor(private router: Router, private authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const lessonId = route.queryParams['lessonId'];

    return this.authService.userHasAccessToCourse(lessonId).pipe(
      map((userHasAccess) => {
        if (userHasAccess) {
          return true;
        } else {
          return this.router.parseUrl('/error/403');
        }
      })
    );
  }
}
