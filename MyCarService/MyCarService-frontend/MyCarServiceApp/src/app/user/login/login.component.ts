import { Component } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {}

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  login(): void {
    if (this.form.invalid) {
      return;
    }

    this.userService.login(this.form.value).subscribe({
      next: (response) => {
        localStorage.setItem('access_token', response.access_token);
        localStorage.setItem('refresh_token', response.refresh_token);
        localStorage.setItem('roles', JSON.stringify(response.roles)); 
        localStorage.setItem('isLogged', 'true');  
        if (response.roles.includes('1')) {
          this.router.navigate(['/home']);
        } else {
          this.router.navigate(['/manage-bookings']);
        }    
      },
      error: (err) => {
        if (err.status === 401) {
          this.form.setErrors({ unauthenticated: true });
        }
      }
    });
  }

}
