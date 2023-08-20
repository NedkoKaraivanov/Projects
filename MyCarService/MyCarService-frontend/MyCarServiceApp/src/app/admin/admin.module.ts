import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material/material.module';
import { SharedModule } from '../shared/shared.module';
import { ManageBookingsComponent } from './manage-bookings/manage-bookings.component';
import { AdminRoutingModule } from './admin-routing.module';
import { UpdateBookingComponent } from './update-booking/update-booking.component';



@NgModule({
  declarations: [
    ManageBookingsComponent,
    UpdateBookingComponent
  ],
  imports: [CommonModule, MaterialModule, ReactiveFormsModule, SharedModule, AdminRoutingModule],
  exports: [ManageBookingsComponent,]
})
export class AdminModule {}
