import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AdminService } from '../admin.service';
import { Booking } from 'src/app/types/booking';

@Component({
  selector: 'app-update-booking',
  templateUrl: './update-booking.component.html',
  styleUrls: ['./update-booking.component.css'],
})
export class UpdateBookingComponent implements OnInit {
  id = '';
  booking: Booking | undefined;
  selectedIsConfirmed: boolean | undefined;
  selectedIsReady: boolean | undefined;
  existingPrice: number | null | undefined;

  constructor(
    private adminService: AdminService,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.adminService.getBooking(Number(this.id)).subscribe({
      next: (booking) => {
        this.form.disable();
        this.selectedIsConfirmed = booking.isConfirmed;
        this.selectedIsReady = booking.isReady;
        this.existingPrice = booking.price;
        this.booking = booking;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  form = this.fb.group({
    isReady: [''],
    isConfirmed: [''],
    price: [0],
  });

  updateBooking() {
    this.adminService
      .updateBooking(Number(this.id), this.form.value)
      .subscribe({
        next: () => {
          this.toastr.success('Booking Updated', 'Success');
          console.log(this.form.value);
          this.router.navigate(['/manage-bookings']);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  deleteBooking() {
    this.adminService.deleteBooking(Number(this.id)).subscribe({
      next: () => {
        this.router.navigate(['/manage-bookings']);
        this.toastr.success('Booking is removed', 'Success');
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  editBooking() {
    if (this.form.disabled) {
      this.form.enable();
    } else {
      this.form.disable();
    }
  }
}
