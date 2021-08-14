import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environment/environment.module';
//import { environment } from 'environments/environment';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EmpLoginService {

  private apiUrl=environment.projecturl;

  constructor(private http:HttpClient) { }

  // geratating tokens
  public generateToken(credentials:any){
    return this.http.post(`${this.apiUrl}/token`,credentials).pipe(
      catchError(this.handleError)
    );
  }
  
  // setting tokens in local storage 
  public loginUser(token){
    localStorage.setItem("token",token);
    return true;
  }

  // checking login status
  public isLoggedIn(){
    let token=localStorage.getItem("token");
    if(token==undefined || token== '' || token==null){
      return false;
    }else{
      return true;
    }
  }
  
  // removing token from Local Storage
  logout(){
    localStorage.removeItem("token");
    return true;
  }

  // retrieving token
  getToken(){
    return localStorage.getItem("token");
  }

  // custom error handler
  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server-side error
      errorMessage = `${error.error.message}`;
    }
    return throwError(errorMessage);
  }

  
}
