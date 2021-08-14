export interface ICustomer {
  userId: number;
  roleId: number;
  firstName: string;
  lastName: string;
  email: String;
  phoneNo: number;
  aadharNo: string;
  policyCount: number;
  dateOfBirth: string;
  createdDate: string;
  modifiedDate: string;
}
export interface IDependent {
  depenedentId: number;
  firstName: string;
  lastName: string;
  middleName: string;
  age: number;
  gender: string;
  userId: number;
  orderId: number;
}

export interface INominee {
  nomineeID: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: number;
  userId: number;
  orderId: number;
}

export interface ICustomerPolicyDetails {
  orderId: number;
  numberOfDependent: number;
  orderDate: Date;
  startDate: Date;
  endDate: Date;
  modifiedDate: Date;
  premiumAmount: number;
  amountPaid: number;
  status: String;
  UserId: number;
  product_id: number;
  policy_id: number;
  paymentFrequency: String;
}

export interface IProduct {
  productId: number;
  productName: String;
  policyId: number;
  numberOfYearsCovered: number;
  productDescription: String;
  sumAssured: number;
}
