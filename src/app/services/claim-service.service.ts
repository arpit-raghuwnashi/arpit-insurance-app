import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environment/environment.module';
import { from, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClaimServiceService 
{
  private apiServerUrl = environment.projecturl;

  body = {}

  constructor(private _http : HttpClient) { } 

  public claimApplication(nomineeName:any, customerName:any, userId:any, nomineeId:any, orderId:any): Observable<any>
  {
    this.body = {nomineeName:nomineeName, customerName:customerName, userId:userId, nomineeId:nomineeId,orderId:orderId}
    console.log(this.body)
    return this._http.post<any>(`${this.apiServerUrl}/addClaim`,this.body)
  }

  public claimDetailVerification(nomineeName:any, customerName:any, userId:any, nomineeId:any, orderId:any): Observable<any>
  {
    this.body = {nomineeName:nomineeName, customerName:customerName, userId:userId, nomineeId:nomineeId,orderId:orderId}
    console.log(this.body)
    return this._http.post<any>(`${this.apiServerUrl}/verifyClaimDetails`,this.body)
  }

  public checkNominee(nomineeId:any): Observable<any>
  {
    this.body = {nomineeId:nomineeId}
    console.log(this.body)
    return this._http.post<any>(`${this.apiServerUrl}/checkNominee`,this.body)
  }

  public checkUser(userId:any): Observable<any>
  {
    this.body = {userId:userId}
    console.log(this.body)
    return this._http.post<any>(`${this.apiServerUrl}/checkUserById`,this.body)
  }

  public checkOrder(orderId:any): Observable<any>
  {
    this.body = {orderId:orderId}
    console.log(this.body)
    return this._http.post<any>(`${this.apiServerUrl}/checkOrder`,this.body)
  }
}
