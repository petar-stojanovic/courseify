import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isAuthenticated?: boolean;

  constructor(private router: Router, private service: AuthService) {}

  ngOnInit(): void {
    this.isAuthenticated = localStorage.getItem('token') != null;
  }

  logout() {
    this.service.logout().subscribe(() => {
      localStorage.removeItem('token');
      this.ngOnInit();
    });
  }
}
