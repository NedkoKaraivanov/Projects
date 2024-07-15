import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { WebSocketService } from 'src/app/web-socket.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  constructor(
    private userService: UserService,
    private router: Router,
    private fb: FormBuilder,
    private webSocketService: WebSocketService
  ) {}

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    phoneNumber: [''],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', [Validators.required]],
  });

  user = {
    email: '',
    phoneNumber: '',
    password: '',
  };

  register(): void {
    if (this.form.invalid) {
      return;
    }
    this.userService.register(this.form.value).subscribe({
      next: (response) => {
        this.webSocketService.connect();
        localStorage.setItem('access_token', response.access_token);
        localStorage.setItem('refresh_token', response.refresh_token);
        localStorage.setItem('roles', JSON.stringify(response.roles));
        localStorage.setItem('isLogged', 'true');  
        this.router.navigate(['/home']);
      },
      error: (err) => {
        if (err.status === 409) {
          this.form.setErrors({ existingUser: true });
        }
      },
    });
  }
}
