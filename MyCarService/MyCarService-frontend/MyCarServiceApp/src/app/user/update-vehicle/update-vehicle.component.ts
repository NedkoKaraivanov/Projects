import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { VehicleService } from '../vehicle.service';
import { Vehicle } from 'src/app/types/vehicle';

@Component({
  selector: 'app-update-vehicle',
  templateUrl: './update-vehicle.component.html',
  styleUrls: ['./update-vehicle.component.css'],
})
export class UpdateVehicleComponent implements OnInit {
  id = '';
  vehicle: Vehicle | undefined;

  constructor(
    private vehicleService: VehicleService,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.vehicleService.getVehicle(Number(this.id)).subscribe({
      next: (vehicle) => {
        this.vehicle = vehicle;
        this.selectedBrand = vehicle.brand;
        this.selectedModel = vehicle.model;
        this.form.disable();
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  form = this.fb.group({
    brand: ['', [Validators.required]],
    model: ['', [Validators.required]],
  });

  selectedBrand = '';
  selectedModel = '';
  brands: string[] = ['Mercedes', 'Bmw', 'Audi', 'Toyota', 'Honda'];

  models: { [brand: string]: string[] } = {
    Mercedes: ['A-Class', 'B-Class', 'C-Class', 'E-Class', 'S-Class'],
    Bmw: [
      '1st-Series',
      '2nd-Series',
      '3rd-Series',
      '4th-Series',
      '5th-Series',
      '6th-Series',
      '7th-Series',
    ],
    Audi: ['A1', 'A3', 'A4', 'A5', 'A6', 'A7', 'A8', 'Q5', 'Q7'],
    Toyota: ['Corolla', 'Avensis', 'Rav4'],
    Honda: ['Civic', 'Accord', 'CR-V'],
  };

  onBrandChange($event: Event): void {
    this.selectedModel = '';
  }

  cancelAddVehicleHandler() {
    this.router.navigate(['/']);
  }

  deleteVehicle() {
    this.vehicleService.deleteVehicle(Number(this.id)).subscribe({
      next: () => {
        this.router.navigate(['/check-vehicles']);
        this.toastr.success('Vehicle is removed', 'Success');
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  editVehicle() {
    if (this.form.disabled) {
      this.form.enable();
    } else {
      this.form.disable();
    }
  }

  updateVehicle() {
    this.vehicleService.updateVehicle({id: Number(this.id), ...this.form.value})
    .subscribe({
      next: () => {
        this.toastr.success('Updated Vehicle', 'Success');
        this.router.navigate(['/check-vehicles']);
      }, 
      error: (err) => {
        console.log(err);
      }
    })
  }
}
