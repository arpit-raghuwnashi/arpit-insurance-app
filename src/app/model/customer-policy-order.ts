export class CustomerPolicyOrder {

    productId:any;
    policyId:any;
    userId:any;
    numberOfDependent:any;
    paymentFrequency:any;
    premiumAmount:any;

    constructor(product_id:any,policy_id:any,userId:any, numberOfDependent:any, paymentFrequency:any, premium:any){
        this.productId=product_id;
        this.policyId= policy_id;
        this.userId = userId;
        this.numberOfDependent= numberOfDependent;
        this.paymentFrequency= paymentFrequency;
        this.premiumAmount = premium;
    }
}
