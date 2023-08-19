import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Vehicle } from '../types/vehicle';

@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  addVehicleUrl: string = 'http://localhost:8080/api/users/vehicles';
  getUserVehiclesUrl: string = 'http://localhost:8080/api/users/vehicles';
  getVehicleUrl: string = 'http://localhost:8080/api/users/vehicles';
  deleteVehicleUrl: string = 'http://localhost:8080/api/users/vehicles';
  updateVehicleUrl: string = 'http://localhost:8080/api/users/vehicles';

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

  deleteVehicle(id: number) {
    return this.http.delete(`${this.deleteVehicleUrl}/${id}`);
  }

  updateVehicle(formValue: {}) {
    return this.http.put<Vehicle>(this.updateVehicleUrl, formValue);
  }
}
