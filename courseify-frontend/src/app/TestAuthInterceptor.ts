import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { Observable, map, switchMap } from 'rxjs';
import * as i0 from '@angular/core';
import { LoggerService } from 'angular-auth-oidc-client/lib/logging/logger.service';
import { ClosestMatchingRouteService } from 'angular-auth-oidc-client/lib/interceptor/closest-matching-route.service';

@Injectable()
export class TestAuthInterceptor implements HttpInterceptor {

    constructor(private oidcSecurityService: OidcSecurityService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
      return this.oidcSecurityService.getIdToken().pipe(
        // Intercept the token and set the Authorization header
        map((token ) => {
          if (token && req.url.includes('/api/')) {
            
            req = req.clone({
              setHeaders: {
                Authorization: `Bearer ${token}`,
              },
            });
          }
          return req;
        }),
        // Continue with the request
        switchMap((requestWithHeaders) => next.handle(requestWithHeaders))
      );
    }
  }