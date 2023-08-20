import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { VehicleService } from '../vehicle.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-add-vehicle',
  templateUrl: './add-vehicle.component.html',
  styleUrls: ['./add-vehicle.component.css'],
})
export class AddVehicleComponent {
  constructor(
    private vehicleService: VehicleService,
    private router: Router,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}

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
    this.form.controls.model.setErrors(null);
  }
  
  cancelAddVehicleHandler() {
    this.router.navigate(['/']);
  }

  addVehicle() {
    this.vehicleService.addVehicle(this.form.value)
    .subscribe({
      next: (response => {
        this.toastr.success('Successfully added vehicle');
        this.form.reset();
        this.form.controls.brand.setErrors(null);
        this.form.controls.model.setErrors(null);
      }),
      error: (err) => {
        console.log(err);
      }
    })
  }
}
