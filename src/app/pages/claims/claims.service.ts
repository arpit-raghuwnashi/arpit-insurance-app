import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environment/environment.module';
//import { environment } from 'environments/environment';
import { Observable } from 'rxjs';
import { IClaim, ICustomerPolicyDetail, IProduct } from './claims';

@Injectable({
  providedIn: 'root'
})
export class ClaimsService {

  private apiServerUrl = environment.projecturl;
  constructor(private http:HttpClient) { }
  public fetchClaims(): Observable<IClaim[]> {
    return this.http.get<IClaim[]>(`${this.apiServerUrl}/fetchClaims`);
  }
  public getCustomerPolicyDetail(
    orderId: any
  ): Observable<ICustomerPolicyDetail[]> {
    return this.http.get<ICustomerPolicyDetail[]>(
      `${this.apiServerUrl}/customerPolicyDetailByOrderId?orderId=` + orderId
    );
  }
  public getP(productId: any): Observable<IProduct[]> {
    return this.http.get<IProduct[]>(
      `${this.apiServerUrl}/fetchProduct?productId=` + productId
    );
  }
  public updateClaimStatus(
    claimId: any
  ): Observable<any> {
    return this.http.get<any>(
      `${this.apiServerUrl}/updateClaimStatus?claimId=` + claimId
    );
  }
}
