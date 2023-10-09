import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { AddVehicleComponent } from './add-vehicle/add-vehicle.component';
import { CheckVehiclesComponent } from './check-vehicles/check-vehicles.component';
import { UpdateVehicleComponent } from './update-vehicle/update-vehicle.component';
import { AddBookingComponent } from './add-booking/add-booking.component';
import { CheckBookingsComponent } from './check-bookings/check-bookings.component';
import { AnonymousActivate } from '../core/guards/anonymous.activate';
import { UserActivate } from '../core/guards/user.activate';
import { ServicesPageComponent } from '../services-page/services-page.component';
import { AboutPageComponent } from '../about-page/about-page.component';
import { UpdateUserBookingComponent } from './update-user-booking/update-user-booking.component';
import { UpdateEmailComponent } from './update-email/update-email.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [AnonymousActivate],
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [AnonymousActivate],
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [UserActivate],
  },
  {
    path: 'services',
    component: ServicesPageComponent,
  },
  {
    path: 'about',
    component: AboutPageComponent,
  },
  {
    path: 'add-vehicle',
    component: AddVehicleComponent,
    canActivate: [UserActivate],
  },
  {
    path: 'check-vehicles',
    component: CheckVehiclesComponent,
    canActivate: [UserActivate],
  },
  {
    path: 'update-vehicle/:id',
    component: UpdateVehicleComponent,
    canActivate: [UserActivate],
  },
  {
    path: 'add-booking',
    component: AddBookingComponent,
    canActivate: [UserActivate],
  },
  {
    path: 'check-bookings',
    component: CheckBookingsComponent,
    canActivate: [UserActivate],
  },
  {
    path: 'update-user-booking/:id',
    component: UpdateUserBookingComponent,
    canActivate: [UserActivate],
  },
  {
    path: 'update-email',
    component: UpdateEmailComponent,
    canActivate: [UserActivate]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
