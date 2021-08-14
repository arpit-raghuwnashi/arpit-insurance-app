import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ContactUsComponent } from '../contact-us/contact-us.component';
import { map } from 'rxjs/internal/operators/map';
import { Observable } from 'rxjs';
import { environment } from 'environment/environment.module';
//import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ContactUsService {

  // httpOtions =  {
  //   headers: new HttpHeaders({'Content-Type':'application/json'})
  // }
  body : any;
  private apiServerUrl = environment.projecturl;

  constructor(private _http: HttpClient) { }

  public contactUS(name:any, email:any, phone:any, message:any): Observable<any>
  {
    this.body = {name:name, email:email, phone:phone, message:message}
    console.log(this.body)
    return this._http.post<any>(`${this.apiServerUrl}/contactUs`,this.body)
  }
  // private contactUs = 'http://localhost:3306/contactUS';

  // public submitContact(enquirer){
  //   return this.http.post<Enquirer>(this.contactUs, enquirer);
  // }

  // PostMessage(input: any) {
  //   return this.http.post(this.api, input, { responseType: 'text' }).pipe(
  //     map(
  //       (response) => {
  //         if (response) {
  //           return response;
  //         }
  //       },
  //       (error: any) => {
  //         return error;
  //       }
  //     )
  //   );
  // }
}
