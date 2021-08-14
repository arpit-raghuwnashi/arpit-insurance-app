import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http'
//import { environment } from 'environments/environment';
import { environment } from 'environment/environment.module';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private apiServerUrl = environment.projecturl;

  constructor(private _http : HttpClient) { }

  public loginUserFromRemote(user : User): Observable<any>
  {
    return this._http.post<any>(`${this.apiServerUrl}/login`,user) 
  }
}
 