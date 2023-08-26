import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Booking } from 'src/app/types/booking';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-manage-bookings',
  templateUrl: './manage-bookings.component.html',
  styleUrls: ['./manage-bookings.component.css'],
})
export class ManageBookingsComponent implements OnInit {
  constructor(
    private adminService: AdminService,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}

  isBookings: boolean | undefined;
  displayedColumns: string[] = [
    'position',
    'brand',
    'model',
    'client',
    'bookingDate',
    'service-type',
    'confirmed',
    'status',
    'price',
    'actions',
  ];
  dataSource: Booking[] = [];

  ngOnInit(): void {
    this.adminService.getAllBookings().subscribe({
      next: (bookings) => {
        if (bookings.length > 0) {
          this.dataSource = bookings;
          this.isBookings = true;
        }
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  updateBooking(id: number) {
    this.router.navigate([`/update-booking/${id}`]);
  }

  getServiceType(serviceType: string) {
    return this.adminService.getServiceType(serviceType);
  }
}
