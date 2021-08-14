import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
//import { environment } from 'environments/environment';
import { environment } from 'environment/environment.module';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ForgotService {

  private apiUrl = environment.projecturl;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  public sendOtpFromRemote(email: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/sendOtp/${email}`, this.httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public verifyOtpFromRemote(confirmOtp: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/verifyOtp/${confirmOtp}`, this.httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  // upadating user passwrod
  public resetPasswrod(credentials: any): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/resetPasswrod`, credentials)
      .pipe(
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
    window.alert(errorMessage);
    return throwError(errorMessage);
  }

}
