import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ManageBookingsComponent } from './manage-bookings/manage-bookings.component';
import { AdminActivate } from '../core/guards/admin.activate';
import { UpdateBookingComponent } from './update-booking/update-booking.component';

const routes: Routes = [
  {
    path: 'manage-bookings',
    component: ManageBookingsComponent,
    canActivate: [AdminActivate]
  },
  {
    path: 'update-booking/:id',
    component: UpdateBookingComponent,
    canActivate: [AdminActivate]
  },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
