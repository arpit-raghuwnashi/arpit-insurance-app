import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from 'environment/environment.module';
//import { environment } from "environments/environment";
import {
  ICustomer,
  ICustomerPolicyDetails,
  IDependent,
  INominee,
  IProduct,
} from "./customers";

@Injectable({
  providedIn: "root",
})
export class CustomersService {
  constructor(private http: HttpClient) { }

  private apiServerUrl = environment.projecturl;

  public fetchCustomers(): Observable<ICustomer[]> {
    return this.http.get<ICustomer[]>(`${this.apiServerUrl}/fetchCustomers`);
  }

  public getDependents(orderId: any): Observable<IDependent[]> {
    return this.http.get<IDependent[]>(
      `${this.apiServerUrl}/fetchDependents?orderId=` + orderId
    );
  }

  public getNominee(orderId: any): Observable<INominee[]> {
    return this.http.get<INominee[]>(
      `${this.apiServerUrl}/fetchNominee?orderId=` + orderId
    );
  }

  public getCustomerPolicyDetails(
    userId: any
  ): Observable<ICustomerPolicyDetails[]> {
    return this.http.get<ICustomerPolicyDetails[]>(
      `${this.apiServerUrl}/customerPolicyDetails?userId=` + userId
    );
  }

  public getP(productId: any): Observable<IProduct> {
    return this.http.get<IProduct>(
      `${this.apiServerUrl}/fetchProduct?productId=` + productId
    );
  }
}
