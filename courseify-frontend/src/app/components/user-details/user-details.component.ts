import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  Validators,
} from '@angular/forms';
import { User } from '../../interfaces/User';
import { AuthService } from '../../services/auth.service';
import { ErrorHandleService } from 'src/app/services/errorHandle.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  user: User | null = null;
  changePassword: boolean = false;
  passwordChangeSucess = false;

  errorMessage: string | null = null;
  userForm: any;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private errorHandleService: ErrorHandleService
  ) {}

  ngOnInit(): void {
    this.getUser();
    this.userForm = new FormGroup({
      oldPassword: new FormControl(null, [
        Validators.required,
        this.checkPassword,
      ]),
      newPassword: new FormControl(null, [
        Validators.required,
        this.checkPassword,
      ]),
    });

    this.errorHandleService.errorMessage$.subscribe((message) => {
      this.errorMessage = message;
    });
  }

  getUser() {
    this.user = this.authService.getLoggedInUser();
  }

  editMode() {
    this.changePassword = true;
  }

  onSubmit(formData: FormGroup, formDirective: FormGroupDirective) {
    const oldPassword = formData.value.oldPassword;
    const newPassword = formData.value.newPassword;
    this.authService
      .changePassword(this.user!!.username, oldPassword, newPassword)
      .subscribe(() => {
        this.passwordChangeSucess = true;
        this.errorMessage
        formDirective.resetForm();
        this.userForm.reset();
        this.errorHandleService.clearErrorMessage()
      });
  }

  checkPassword(control: { value: any }) {
    let enteredPassword = control.value;
    let passwordCheck = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{6,})/;
    return !passwordCheck.test(enteredPassword) && enteredPassword
      ? { requirements: true }
      : null;
  }

  checkValidation(input: string) {
    const validation =
      this.userForm.get(input)?.invalid &&
      (this.userForm.get(input)?.dirty || this.userForm.get(input)?.touched);
    return validation;
  }

  getPasswordErrors(input: string) {
    return this.userForm.get(input)?.hasError('required')
      ? 'This field is required!'
      : this.userForm.get(input)?.hasError('requirements')
      ? 'Password needs to be at least six characters, one uppercase letter and one number'
      : '';
  }
}
