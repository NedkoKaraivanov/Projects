import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { Vehicle } from 'src/app/types/vehicle';
import { VehicleService } from '../vehicle.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-check-vehicles',
  templateUrl: './check-vehicles.component.html',
  styleUrls: ['./check-vehicles.component.css'],
})
export class CheckVehiclesComponent implements OnInit {
  constructor(private vehicleService: VehicleService, private router: Router) {}

  isVehicles: boolean | undefined;
  displayedColumns: string[] = ['position','brand', 'model', 'actions'];
  dataSource: Vehicle[] = [];
  currentVehicle: Vehicle | undefined;

  ngOnInit(): void {
    this.vehicleService.getUserVehicles().subscribe({
      next: (vehicles) => {
        console.log(vehicles);
        if (vehicles.length) {
          this.isVehicles = true;
          this.dataSource = vehicles;
        }
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
