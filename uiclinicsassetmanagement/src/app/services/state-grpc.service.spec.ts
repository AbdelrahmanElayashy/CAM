import { TestBed } from '@angular/core/testing';

import { StateGRPCService } from './state-grpc.service';

describe('StateGRPCService', () => {
  let service: StateGRPCService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StateGRPCService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
