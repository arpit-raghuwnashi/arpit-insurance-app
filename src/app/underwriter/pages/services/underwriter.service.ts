import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IUser } from 'app/underwriter/pages/model/user';
import { environment } from 'environment/environment.module';
//import { environment } from 'environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PolicyResponse } from '../model/policyStatusResponse';

@Injectable({
  providedIn: 'root'
})
export class UnderwriterService {

  private apiServerUrl = environment.projecturl;

  constructor(private http: HttpClient) { }

  // retrieving user data
  public getUnderWriter(email): Observable<any> {
    return this.http.get<IUser>(`${this.apiServerUrl}/getUnderWriter/${email}`).pipe(
      catchError(this.handleError)
    );
  }

  // updating underwriter data
  public updateUnderWriter(user: IUser): Observable<any> {
    return this.http.put<any>(`${this.apiServerUrl}/updateUnderWriter`, user).pipe(
      catchError(this.handleError)
    );
  }

  // Deactive underwriter account
  // remain
  public deactiveUnderwriter(userId): Observable<any> {
    return this.http.delete<void>(`${this.apiServerUrl}/deactiveUnderwriter/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  // retrieving Pendin customer policy order
  public getPendingCustomerPolicyDetails(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/getPendingCustomerPolicyDetails`).pipe(
      catchError(this.handleError)
    );
  }

   // retrieving Pendin customer policy order
   public getDeclineCustomerPolicyDetails(): Observable<any> {
    return this.http.get<any>(`${this.apiServerUrl}/getDeclineCustomerPolicyDetails`).pipe(
      catchError(this.handleError)
    );
  }

  // change status of request might be Approved/Decline
  public updatePendingStatus(policyStatusResponse:PolicyResponse): Observable<any> {
    return this.http.put<void>(`${this.apiServerUrl}/updatePendingStatus`,policyStatusResponse).pipe(
      catchError(this.handleError)
    );
  }

  // retrieving all Products details
  public getProductDetails() :Observable<any>{
    return this.http.get<any>(`${this.apiServerUrl}/products`).pipe(
      catchError(this.handleError)
    );
  }

  // retrieve dashboard counts
  public getDashboardCounts():Observable<any>{
    return this.http.get<void>(`${this.apiServerUrl}/dashboradCounts`).pipe(
      catchError(this.handleError)
    );
  }


  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server-side error
      errorMessage = `${error.error.message}`;
    }
    //  window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
