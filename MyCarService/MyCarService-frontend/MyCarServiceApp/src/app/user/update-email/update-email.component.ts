import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from '../user.service';
import { UpdateEmailDto } from 'src/app/types/updateEmailDto';
import { SidenavService } from 'src/app/sidenav.service';

@Component({
  selector: 'app-update-email',
  templateUrl: './update-email.component.html',
  styleUrls: ['./update-email.component.css'],
})
export class UpdateEmailComponent {
  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService,
    private sidenavService: SidenavService,
  ) {}

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    confirmEmail: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  userData: UpdateEmailDto = {
    newEmail: '',
    password: ''
  }

  cancelUpdateHandler(): void {
    this.form.reset();
    this.router.navigate(['/profile']);
  }

  updateEmailHandler(): void {
    if (this.form.invalid) {
      return;
    }

    this.userData.newEmail = this.form.get('email')?.value as string;
    this.userData.password = this.form.get('password')?.value as string;

    this.userService.updateEmail(this.userData).subscribe({
      next: (response) => {
        this.sidenavService.close();
        this.toastr.success('Email Successfully updated', 'Success', );
        this.userService.logout().subscribe({
          next: () => {
            this.router.navigate(['/']);
          }
        });

      },
      error: (error) => {
        if (error.status === 409) {
          this.form.setErrors({ userExists: true });
        } else if (error.status === 401) {
          this.form.setErrors({ unauthenticated: true });
        }
      }
    })
  }
}
