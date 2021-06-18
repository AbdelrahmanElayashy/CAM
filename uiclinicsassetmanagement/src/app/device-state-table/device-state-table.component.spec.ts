import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceStateTableComponent } from './device-state-table.component';

describe('DeviceStateTableComponent', () => {
  let component: DeviceStateTableComponent;
  let fixture: ComponentFixture<DeviceStateTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeviceStateTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceStateTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
