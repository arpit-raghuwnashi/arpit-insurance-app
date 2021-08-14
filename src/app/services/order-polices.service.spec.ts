import { TestBed } from '@angular/core/testing';

import { OrderPolicesService } from './order-polices.service';

describe('OrderPolicesService', () => {
  let service: OrderPolicesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrderPolicesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
