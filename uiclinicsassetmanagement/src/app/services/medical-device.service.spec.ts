import { TestBed } from '@angular/core/testing';

import { MedicalDeviceService } from './medical-device.service';

describe('MedicalDeviceService', () => {
  let service: MedicalDeviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MedicalDeviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
