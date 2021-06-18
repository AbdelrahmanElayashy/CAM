import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeAvailabilityDateDialogComponent } from './change-availability-date-dialog.component';

describe('ChangeAvailabilityDateDialogComponent', () => {
  let component: ChangeAvailabilityDateDialogComponent;
  let fixture: ComponentFixture<ChangeAvailabilityDateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangeAvailabilityDateDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeAvailabilityDateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
