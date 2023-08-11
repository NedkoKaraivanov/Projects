import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { matchPasswordsValidator } from 'src/app/shared/validators/match-passwords-validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  constructor(
    private userService: UserService,
    private router: Router,
    private fb: FormBuilder
  ) {}

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    rePassword: ['', [Validators.required]],

  }, {validators: [matchPasswordsValidator('password', 'rePassword')]});

  // user = {
  //   email: '',
  //   password: '',
  // };
  // confirmPassword = '';

  // onSubmit() {
  //   if (this.confirmPassword !== this.user.password) {
  //     // Handle password mismatch
  //     return;
  //   }

  //   // Handle form submission
  // }
}
