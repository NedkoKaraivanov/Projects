import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckBookingsComponent } from './check-bookings.component';

describe('CheckBookingsComponent', () => {
  let component: CheckBookingsComponent;
  let fixture: ComponentFixture<CheckBookingsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckBookingsComponent]
    });
    fixture = TestBed.createComponent(CheckBookingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
