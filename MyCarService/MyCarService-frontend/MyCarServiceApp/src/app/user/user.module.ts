import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { UserRoutingModule } from './user-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { AddVehicleComponent } from './add-vehicle/add-vehicle.component';
import { CheckVehiclesComponent } from './check-vehicles/check-vehicles.component';
import { UpdateVehicleComponent } from './update-vehicle/update-vehicle.component';
import { AddBookingComponent } from './add-booking/add-booking.component';
import { CheckBookingsComponent } from './check-bookings/check-bookings.component';
import { UpdateUserBookingComponent } from './update-user-booking/update-user-booking.component';
import { UpdateEmailComponent } from './update-email/update-email.component';

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    AddVehicleComponent,
    CheckVehiclesComponent,
    UpdateVehicleComponent,
    AddBookingComponent,
    CheckBookingsComponent,
    UpdateUserBookingComponent,
    UpdateEmailComponent
  ],
  imports: [
    CommonModule, UserRoutingModule, FormsModule, MaterialModule, ReactiveFormsModule, SharedModule
  ],
  exports: [LoginComponent, RegisterComponent, ProfileComponent]
})
export class UserModule { }
