import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { confirmPasswordValidator } from 'src/app/shared/validators/match-passwords-validator';

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

  form = this.fb.group(
    {
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: [''],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
    },
  );

  user = {
    email: '',
    phoneNumber: '',
    password: '',
  };

  register(): void {
    if (this.form.invalid) {
      return;
    }
    this.userService.register(this.form.value);
    this.router.navigate(['/home']);
  }
}

