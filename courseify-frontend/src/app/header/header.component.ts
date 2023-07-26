import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  isAuthenticated: boolean = localStorage.getItem('token') != null;

  constructor(
    private oidcSecurityService: OidcSecurityService,
    private router: Router
  ) {}

  logout(){
    localStorage.removeItem("token")
    this.router.navigateByUrl("/")
  }
}
