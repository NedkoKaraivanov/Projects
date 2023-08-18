import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckVehiclesComponent } from './check-vehicles.component';

describe('CheckVehiclesComponent', () => {
  let component: CheckVehiclesComponent;
  let fixture: ComponentFixture<CheckVehiclesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CheckVehiclesComponent]
    });
    fixture = TestBed.createComponent(CheckVehiclesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
