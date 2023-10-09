import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Booking } from 'src/app/types/booking';
import { Vehicle } from 'src/app/types/vehicle';
import { BookingService } from '../booking.service';
import { VehicleService } from '../vehicle.service';

@Component({
  selector: 'app-update-user-booking',
  templateUrl: './update-user-booking.component.html',
  styleUrls: ['./update-user-booking.component.css'],
})
export class UpdateUserBookingComponent implements OnInit {
  constructor(
    private vehicleService: VehicleService,
    private bookingService: BookingService,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private route: ActivatedRoute
  ) {}

  id = '';
  booking: Booking | undefined;
  vehicles: Vehicle[] = [];
  selectedVehicle: Vehicle | undefined;
  selectedBookingDate: Date | undefined;
  selectedServiceType: string | undefined;
  selectedDescription: string | undefined;
  minDate = new Date();
  serviceTypes: string[] = [
    'Diagnostics',
    'Oil Change',
    'Suspension Work',
    'Tire Change',
  ];

  form = this.fb.group({
    vehicle: [null, [Validators.required]],
    bookingDate: [null, [Validators.required]],
    serviceType: ['', [Validators.required]],
    description: [''],
  });

  compareObjects(o1: Vehicle, o2: Vehicle): boolean {
    return o1.id === o2.id && o1.brand === o2.brand && o1.model === o2.model;
  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.bookingService.getUserBooking(Number(this.id)).subscribe({
      next: (booking) => {
        this.selectedVehicle = booking.vehicle;
        this.selectedBookingDate = booking.bookingDate;
        this.selectedServiceType = this.bookingService.getServiceType(
          booking.serviceType
        );
        this.selectedDescription = booking.description;
        this.booking = booking;
        this.form.disable();
      },
      error: (err) => {
        console.log(err);
      },
    });

    this.vehicleService.getUserVehicles().subscribe({
      next: (vehicles) => {
        this.vehicles = vehicles;
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  editBooking() {
    this.form.enable();
  }

  updateBooking() {
    this.bookingService
      .updateBooking(Number(this.id), this.form.value)
      .subscribe({
        next: () => {
          this.toastr.success('Booking Updated', 'Success');
          this.form.reset();
          this.router.navigate(['/check-bookings']);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  deleteBooking() {
    this.bookingService.deleteBooking(Number(this.id)).subscribe({
      next: () => {
        this.toastr.success('Booking is removed', 'Success');
        this.router.navigate(['/check-bookings']);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
