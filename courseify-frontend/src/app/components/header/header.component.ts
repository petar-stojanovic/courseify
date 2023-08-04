import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { User } from '../../interfaces/User';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isAuthenticated?: boolean;
  user: User | undefined;

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    this.isAuthenticated = this.authService.isAuthenticated();

    if(this.isAuthenticated){

      this.authService.getUserByToken().subscribe((result) => {
        this.user = result;
        console.log(this.user);
      });
    }
  }

  logout() {
    this.authService.logout().subscribe(() => {
      localStorage.removeItem('token');
      this.ngOnInit();
    });
  }
}
