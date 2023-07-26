import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'courseify-frontend';

  // constructor(private oidcSecurityService: OidcSecurityService) {}

  // ngOnInit(): void {
  //   this.oidcSecurityService
  //     .checkAuth()
  //     .subscribe(({ isAuthenticated, userData, accessToken }) => {
  //       console.log('app authenticated', isAuthenticated);
  //       console.log(`Current access token is '${accessToken}'`);
  //     });
  // }
}
