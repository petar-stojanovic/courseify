import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { User } from '../../interfaces/User';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  user: User | undefined;
  changePassword: boolean = false;
  userForm = new FormGroup({
    password: new FormControl('', [Validators.required]),
    confirmPassword: new FormControl('', [Validators.required]),
  });

  constructor(private fb: FormBuilder, private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getUserByToken().subscribe((result) => {
      this.user = result;
    });
  }

  editMode() {
    this.changePassword = true;
  }

  submit() {
    console.log(this.userForm.get('password')?.value);
    console.log(this.userForm.get('confirmPassword')?.value);
  }
}
