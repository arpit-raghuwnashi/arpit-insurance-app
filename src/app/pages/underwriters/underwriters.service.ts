import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { IUnderwriter, UnderwriterRegister } from "./underwriters";
import { HttpClient } from "@angular/common/http";
import { environment } from 'environment/environment.module';

@Injectable({
  providedIn: "root",
})
export class UnderwritersService {
  private apiServerUrl = environment.projecturl;

  constructor(private http: HttpClient) {}

  public getUnderwriters(): Observable<IUnderwriter[]> {
    return this.http.get<IUnderwriter[]>(
      `${this.apiServerUrl}/viewunderwriters/2`
    );
  }

  public create(create: UnderwriterRegister) {
    return this.http.post<any>(
      `${this.apiServerUrl}/underwriterregister`,
      create
    );
  }

  public deleteunderwriter(userid: any) {
    return this.http.delete(
      `${this.apiServerUrl}/deactiveUnderwriter/` + userid
    );
  }
}
