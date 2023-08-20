import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Booking } from '../types/booking';

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  apiUrl: string = 'http://localhost:8080/api/users/bookings';

  constructor(private http: HttpClient) {}

  addBooking(formValue: {}) {
    return this.http.post<Booking>(this.apiUrl, formValue);
  }

  getUserBookings() {
    return this.http.get<Booking[]>(this.apiUrl);
  }
}
