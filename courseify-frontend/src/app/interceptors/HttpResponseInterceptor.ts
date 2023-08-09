import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ErrorHandleService } from '../services/errorHandle.service';

@Injectable()
export class HttpResponseInterceptor implements HttpInterceptor {
  authErrorMessages: { [key: string]: string } = {
    invalidCredentials: 'Invalid Credentials. Please Try Again',
    wrongPassword: 'Wrong Password. Please Try Again',
    usernameExists: 'Username already exists. Please Try Again',
    emailExists: 'Email already exists. Please Try Again',
  };

  constructor(
    private router: Router,
    private errorHandleService: ErrorHandleService
  ) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        console.error('ERROR: ', error.error.error);
        const authErrorReason = error.headers.get('X-Auth-Error-Reason');
        console.error(authErrorReason);

        if (authErrorReason && this.authErrorMessages[authErrorReason]) {
          this.errorHandleService.setErrorMessage(
            this.authErrorMessages[authErrorReason]
          );
        } else {
          console.log('ROUTER');
          this.router.navigate(['/error', error.status]);
        }

        return throwError(() => error);
      })
    );
  }
}
