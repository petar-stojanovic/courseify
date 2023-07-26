import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  isAuthenticated: boolean = localStorage.getItem('token') != null;

  constructor(
    private router: Router,
    private service: AuthService
  ) {}

  logout(){
    // localStorage.removeItem("token")
    this.service.logout()
    this.router.navigateByUrl("/")
  }
}
