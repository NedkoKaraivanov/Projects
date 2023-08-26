import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BookingService } from '../booking.service';
import { Booking } from 'src/app/types/booking';

@Component({
  selector: 'app-check-bookings',
  templateUrl: './check-bookings.component.html',
  styleUrls: ['./check-bookings.component.css'],
})
export class CheckBookingsComponent implements OnInit {
  constructor(
    private bookingService: BookingService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  isBookings: boolean | undefined;
  displayedColumns: string[] = [
    'position',
    'brand',
    'model',
    'serviceType',
    'bookingDate',
    'confirmed',
    'status',
    'price',
    'actions',
  ];
  dataSource: Booking[] = [];

  ngOnInit(): void {
    this.bookingService.getUserBookings().subscribe({
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

  getServiceType(serviceType: string): string {
    return this.bookingService.getServiceType(serviceType);
  }

  updateBooking(id: number) {
    this.router.navigate([`/update-user-booking/${id}`]);
  }
}
