import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Vehicle } from '../types/vehicle';

@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  addVehicleUrl: string = 'http://localhost:8080/api/users/vehicles';
  getUserVehiclesUrl: string = 'http://localhost:8080/api/users/vehicles';
  getVehicleUrl: string = 'http://localhost:8080/api/users/vehicle';

  constructor(private http: HttpClient) {}

  addVehicle(formValue: {}) {
    return this.http.post<Vehicle>(this.addVehicleUrl, formValue);
  }

  getUserVehicles() {
    return this.http.get<Vehicle[]>(this.getUserVehiclesUrl);
  }

  getVehicle(id: number) {
    return this.http.get<Vehicle>(`${this.getVehicleUrl}/${id}`);
  }
}
