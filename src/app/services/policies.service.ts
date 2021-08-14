import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
//import { environment } from 'environments/environment';
import { environment } from 'environment/environment.module';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PoliciesService {
  // private config = { headers: new HttpHeaders().append('Content-Type', 'application/json; charset=utf-8') };
  private apiServerUrl = environment.projecturl;

  constructor(private _http : HttpClient) { }

  public getPolicyByIdFromRemote(id : String): Observable<any>{
    const body = {"policyId":id}
    return this._http.post<any>(`${this.apiServerUrl}/getProductByPolicyId`,body)
  }

  public getUserByEmail(email: String): Observable<any>{
    const body = {"email":email}
    return this._http.post<any>(`${this.apiServerUrl}/getUserByEmail`,body)
  }

  public checkUserWithOrderFromRemote(userId : Number,productId:Number): Observable<any>{
    const body = {"productId":productId,"userId":userId}
    return this._http.post<any>(`${this.apiServerUrl}/checkUserWithProduct`,body)
  }
}
