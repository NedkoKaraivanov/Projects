import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Vehicle } from 'src/app/types/vehicle';
import { BookingService } from '../booking.service';
import { VehicleService } from '../vehicle.service';
import * as moment from 'moment';
import { MatDatepickerInput } from '@angular/material/datepicker';

@Component({
  selector: 'app-add-booking',
  templateUrl: './add-booking.component.html',
  styleUrls: ['./add-booking.component.css'],
})
export class AddBookingComponent implements OnInit {
  @ViewChild(MatDatepickerInput) datepickerInput: MatDatepickerInput<Date> | undefined;

  constructor(
    private bookingService: BookingService,
    private vehicleService: VehicleService,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}

  vehicles: Vehicle[] = [];
  selectedVehicle: Vehicle | undefined;
  isVehicles: boolean | undefined;
  selectedServiceType = '';
  minDate = new Date();
  serviceTypes: string[] = [
    'Diagnostics',
    'Oil Change',
    'Suspension Work',
    'Tire Change',
  ];

  form = this.fb.group({
    vehicle: ['', [Validators.required]],
    bookingDate: ['', [Validators.required]],
    serviceType: ['', [Validators.required]],
    description: [''],
  });

  ngOnInit(): void {
    this.vehicleService.getUserVehicles().subscribe({
      next: (vehicles) => {
        if (vehicles.length === 0) {
          this.isVehicles = false;
          this.form.disable();
        } else {
          this.isVehicles = true;
          this.vehicles = vehicles;
        }
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  addBooking() {
    this.bookingService.addBooking(this.form.value).subscribe({
      next: (response) => {
        console.log(this.datepickerInput?.value);
        
        this.form.reset();
        this.toastr.success('You have made a booking', 'success');
        this.form.controls.vehicle.setErrors(null);
        this.form.controls.bookingDate.setErrors(null);
        this.form.controls.serviceType.setErrors(null);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  backToHome() {
    this.router.navigate(['/']);
  }
}
