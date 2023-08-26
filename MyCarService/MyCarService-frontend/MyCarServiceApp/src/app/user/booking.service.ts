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

  updateBooking(id: number, formValue: {}) {
    return this.http.put<Booking>(`${this.apiUrl}/${id}`, formValue);
  }

  deleteBooking(id: number) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  getUserBooking(id: number) {
    return this.http.get<Booking>(`${this.apiUrl}/${id}`);
  }

  getUserBookings() {
    return this.http.get<Booking[]>(this.apiUrl);
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
