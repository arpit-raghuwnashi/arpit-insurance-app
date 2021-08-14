import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
//import { environment } from 'environments/environment';
import { environment } from 'environment/environment.module';
import { Observable } from 'rxjs';
import { CustomerPolicyOrder } from '../model/customer-policy-order';
import { Dependent } from '../model/dependent';
import { Nominee } from '../model/nominee';

@Injectable({
  providedIn: 'root'
})
export class OrderPolicesService {
 // private config = { headers: new HttpHeaders().append('Content-Type', 'application/json; charset=utf-8') };
 private apiServerUrl = environment.projecturl;

 constructor(private _http : HttpClient) { }

public getPolicyByIdFromRemote(id : String): Observable<any>{
   console.log("working in services")
   const body = {"productId":id}
   return this._http.post<any>(`${this.apiServerUrl}/getProductByProductId`,body)
}

public getUserByEmail(email: String): Observable<any>{
  const body = {"email":email}
  return this._http.post<any>(`${this.apiServerUrl}/getUserByEmail`,body)
}

public submitApplication
(
  customerPolicyOrder:CustomerPolicyOrder,
  nominee:Nominee,
  dependent:Dependent[]
  ):Observable<any>
  {
  const body = {
    "customerPolicyDetails":customerPolicyOrder,
    "nominee":nominee,
    "dependent":dependent
  }
  return this._http.post<any>(`${this.apiServerUrl}/addPolicyDetails`,body)
  }


  public checkPolicyApproval(customerPolicyOrder:CustomerPolicyOrder){
    const body = {"numberOfDependent":customerPolicyOrder.numberOfDependent,
                  "paymentFrequency": customerPolicyOrder.paymentFrequency,
                  "policyId": customerPolicyOrder.policyId,
                  "premiumAmount": customerPolicyOrder.premiumAmount,
                  "productId": customerPolicyOrder.productId,
                  "userId": customerPolicyOrder.userId
                  }
    console.log(body)
    return this._http.post<any>(`${this.apiServerUrl}/checkApplicationApproval`,body)
  }

}
