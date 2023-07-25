import { HttpParams } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';

@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://dev-qhpq6har3ade18l7.us.auth0.com',
            redirectUrl: 'http://localhost:4200/',
            clientId: 'v18j4NbapYFBBVPimcu71PKXbTjwFiGL',
            scope: 'openid profile offline_access',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
            secureRoutes: ['http://localhost:8080/'],
            customParamsAuthRequest: {
                audience: 'http://localhost:8080', 
            },
            
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}
