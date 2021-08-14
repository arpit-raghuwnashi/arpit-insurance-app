import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "environment/environment.module";
import { Observable } from "rxjs";
import { ICount } from "./dashboard";

@Injectable({
  providedIn: "root",
})
export class DashboardService {

  private apiServerUrl = environment.projecturl;

  constructor(private http: HttpClient) {}
  public count(): Observable<ICount> 
  {
    return this.http.get<ICount>(`${this.apiServerUrl}/total`);
  }
}
