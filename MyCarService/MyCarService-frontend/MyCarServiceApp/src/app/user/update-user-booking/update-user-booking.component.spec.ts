import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateUserBookingComponent } from './update-user-booking.component';

describe('UpdateUserBookingComponent', () => {
  let component: UpdateUserBookingComponent;
  let fixture: ComponentFixture<UpdateUserBookingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateUserBookingComponent]
    });
    fixture = TestBed.createComponent(UpdateUserBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
