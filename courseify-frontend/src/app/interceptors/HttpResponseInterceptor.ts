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
        console.error('ERROR: ', error.error.message);
        const authErrorReason = error.headers.get('X-Auth-Error-Reason');
        console.error(authErrorReason);

        if (authErrorReason === 'invalidCredentials') {
          this.errorHandleService.setErrorMessage(
            'Invalid Credentials. Please Try Again'
          );
        } else if (authErrorReason === 'wrongPassword') {
          this.errorHandleService.setErrorMessage(
            'Wrong Password. Please Try Again'
          );
        } else if (authErrorReason === 'usernameExists') {
          this.errorHandleService.setErrorMessage(
            'Username already exists. Please Try Again'
          );
        } else if (authErrorReason === 'emailExists') {
          this.errorHandleService.setErrorMessage(
            'Email already exists. Please Try Again'
          );
        } else {
          console.log('ROUTER');
          // this.router.navigate(['/error', error.status]);
        }

        return throwError(() => error);
      })
    );
  }
}
