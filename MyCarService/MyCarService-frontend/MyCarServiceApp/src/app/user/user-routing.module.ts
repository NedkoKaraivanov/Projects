import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { AddVehicleComponent } from './add-vehicle/add-vehicle.component';
import { CheckVehiclesComponent } from './check-vehicles/check-vehicles.component';
import { UpdateVehicleComponent } from './update-vehicle/update-vehicle.component';
import { AddBookingComponent } from './add-booking/add-booking.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    // canActivate: [AuthActivate],
  },
  {
    path: 'register',
    component: RegisterComponent,
    // canActivate: [AuthActivate],
  },
  {
    path: 'profile',
    component: ProfileComponent,
    // canActivate: [AuthActivate],
  },
  {
    path: 'add-vehicle',
    component: AddVehicleComponent,
  },
  {
    path: 'check-vehicles',
    component: CheckVehiclesComponent,
  },
  {
    path: 'update-vehicle/:id',
    component: UpdateVehicleComponent,
  },
  {
    path: 'add-booking',
    component: AddBookingComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
