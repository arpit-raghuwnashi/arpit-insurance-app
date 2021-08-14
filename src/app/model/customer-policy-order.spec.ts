import { CustomerPolicyOrder } from './customer-policy-order';

describe('CustomerPolicyOrder', () => {
  it('should create an instance', () => {
    expect(new CustomerPolicyOrder("","","","","","")).toBeTruthy();
  });
});
