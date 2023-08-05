import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../interfaces/User';
import { AuthService } from '../../services/auth.service';

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
      this.user = this.authService.getLoggedInUser()!!
    }
  }

  logout() {
    this.authService.logout().subscribe(() => {
      this.isAuthenticated = false
    });
  }

}
