import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
//import { environment } from 'environments/environment';
import { environment } from 'environment/environment.module';
import { IUser } from 'app/underwriter/pages/model/user';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  private apiServerUrl = environment.projecturl;

  constructor(private _http:HttpClient) { }

  public getUserData(userId:any):Observable<any>
  {
    const body={"userId":userId}
    return this._http.post<any>(`${this.apiServerUrl}/fetchUserProducts`,body);
  }

  public getAdminByEmail(email): Observable<any> 
  {
    return this._http.get<IUser>(`${this.apiServerUrl}/getAdminByEmail/${email}`);
  }

  public updateAdminProfile(user: IUser): Observable<any> 
  {
    return this._http.put<any>(`${this.apiServerUrl}/updateAdminProfile`, user);
  }
}
