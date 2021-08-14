export interface IClaim{
    claimId:number;
    userId:number;
    orderId:number;
    nomineeId:number;
    createdDate:string;
    modifiedDate:string;
    nomineeName:string;
    customerName:string;
    claimStatus:string;
}
export interface ICustomerPolicyDetail {
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
    productId: number;
    policyId: number;
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
  