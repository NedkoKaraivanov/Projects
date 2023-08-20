import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { BookingService } from '../booking.service';
import { VehicleService } from '../vehicle.service';
import { Vehicle } from 'src/app/types/vehicle';
import { Booking } from 'src/app/types/booking';

@Component({
  selector: 'app-check-bookings',
  templateUrl: './check-bookings.component.html',
  styleUrls: ['./check-bookings.component.css'],
})
export class CheckBookingsComponent implements OnInit{
  constructor(
    private bookingService: BookingService,
    private vehicleService: VehicleService,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}
  
  isBookings: boolean | undefined;
  displayedColumns: string[] = ['position', 'brand', 'model', 'confirmed', 'status', 'price'];
  dataSource: Booking[] = [];
  


  ngOnInit(): void {
    this.bookingService.getUserBookings()
    .subscribe({
      next: (bookings) => {
        if (bookings.length > 0) {
          console.log(bookings);
          
          this.dataSource = bookings;
          this.isBookings = true;
        } 
      }, 
      error: (err) => {
        console.log(err);
      }
    })
  
  }
}
