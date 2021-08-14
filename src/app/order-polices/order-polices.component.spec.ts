import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderPolicesComponent } from './order-polices.component';

describe('OrderPolicesComponent', () => {
  let component: OrderPolicesComponent;
  let fixture: ComponentFixture<OrderPolicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderPolicesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderPolicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
