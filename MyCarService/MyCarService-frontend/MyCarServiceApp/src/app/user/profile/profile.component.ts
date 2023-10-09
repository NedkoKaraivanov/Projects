import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Profile } from 'src/app/types/profile';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
    private toastr: ToastrService
  ) {}

  isEditMode: boolean = false;

  profileDetails: Profile = {
    email: '',
    phoneNumber: '',
    firstName: '',
    lastName: '',
  };

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    phoneNumber: [''],
    firstName: [''],
    lastName: [''],
  });

  ngOnInit(): void {
    let email = '';
    let phoneNumber = '';
    let firstName = '';
    let lastName = '';

    this.userService.getProfile().subscribe({
      next: (profile) => {
        email = profile.email;
        phoneNumber = profile.phoneNumber;
        firstName = profile.firstName;
        lastName = profile.lastName;
        this.profileDetails = {
          email,
          phoneNumber,
          firstName,
          lastName,
        };
        this.form.setValue(this.profileDetails);
      },
    });
  }

  toggleEditMode(): void {
    this.isEditMode = !this.isEditMode;
  }

  cancelEditHandler(): void {
    this.form.setValue(this.profileDetails);
    this.isEditMode = false;
  }

  updateEmailHandler(): void {
    this.router.navigate(['/update-email'])
  }

  saveProfileHandler(): void {
    if (this.form.invalid) {
      return;
    }

    this.profileDetails = { ...this.form.value } as Profile;
    const { email, phoneNumber, firstName, lastName } = this.profileDetails;

    this.userService.updateProfile(this.form.value).subscribe({
      next: () => {
        this.toastr.success('Profile updated', 'Success');
        this.isEditMode = false;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
