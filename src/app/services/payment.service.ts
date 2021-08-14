import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
//import { environment } from 'environments/environment';
import { environment } from 'environment/environment.module';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService 
{

  private apiServerUrl = environment.projecturl;

  body:any = {}

  constructor(private _http : HttpClient) { }

    public payAmount(amount:any, orderId:any): Observable<any>
    {
      this.body = {"amountPaid":amount,"orderId":orderId};
      console.log(this.body);
      return this._http.post<any>(`${this.apiServerUrl}/addPayment`,this.body);
    }

    public getInvoice(orderId:any): Observable<any>
    {
      this.body = {"orderId":orderId};
      console.log(this.body);
      return this._http.post<any>(`${this.apiServerUrl}/getInvoiceData`,this.body);
    }
}
