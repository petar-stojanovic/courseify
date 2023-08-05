import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { User } from '../../interfaces/User';
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  user?: User;
  changePassword: boolean = false;
  isAuthenticated?: boolean;

  userForm = new FormGroup({
    password: new FormControl('', [Validators.required]),
    confirmPassword: new FormControl('', [Validators.required]),
  });

  constructor(private fb: FormBuilder, private authService: AuthService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    console.log(this.user);
    // this.isAuthenticated = this.authService.isAuthenticated();

    // this.authService.getUserByToken().subscribe((result) => {
    //   this.user = result;
    // });

    const userJson = this.route.snapshot.paramMap.get('userJson');
    this.user = JSON.parse(decodeURIComponent(userJson!!));

  }

  editMode() {
    this.changePassword = true;
  }

  submit() {
    console.log(this.userForm.get('password')?.value);
    console.log(this.userForm.get('confirmPassword')?.value);
  }
}
