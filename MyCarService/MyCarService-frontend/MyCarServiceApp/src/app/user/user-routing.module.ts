import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { AddVehicleComponent } from './add-vehicle/add-vehicle.component';
import { CheckVehiclesComponent } from './check-vehicles/check-vehicles.component';

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
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
