import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Booking } from '../types/booking';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  getAllBookingsUrl = 'http://localhost:8080/api/users/all-bookings';
  getBookingUrl = 'http://localhost:8080/api/users/bookings';
  updateBookingUrl = 'http://localhost:8080/api/users/bookings';
  deleteBookingUrl = 'http://localhost:8080/api/users/bookings';

  constructor(private http: HttpClient) {}

  getAllBookings() {
    return this.http.get<Booking[]>(this.getAllBookingsUrl);
  }

  getBooking(id: number) {
    return this.http.get<Booking>(`${this.getBookingUrl}/${id}`);
  }

  updateBooking(id: number, formValue: {}) {
    return this.http.put<Booking>(`${this.updateBookingUrl}/${id}`, formValue);
  }

  deleteBooking(id: number) {
    return this.http.delete(`${this.deleteBookingUrl}/${id}`);
  }

  getServiceType(serviceType: string) {
    switch (serviceType) {
      case 'DIAGNOSTICS':
        return 'Diagnostics';
      case 'OIL_CHANGE':
        return 'Oil Change';
      case 'TIRE_CHANGE':
        return 'Tire Change';
      case 'SUSPENSION_WORK':
        return 'Suspension Work';
      default:
        return '';
    }
  }
}
