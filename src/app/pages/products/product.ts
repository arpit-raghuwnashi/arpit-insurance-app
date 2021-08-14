export interface Product
{
    productId : number;
    policyId : number;
    productName : string;
    minAgeLimit : number;
    maxAgeLimit : number;
    numberOfYearsCovered : number;
    productDescription : string;
    createdDate : string;
    modifiedDate : string;
    sumAssured : number;
    minNumDependents : number;
    maxNumDependents : number;
}