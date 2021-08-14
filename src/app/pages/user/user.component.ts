import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EmpLoginService } from 'app/emp-login/emp-login.service';
import { UserProfileService } from 'app/services/user-profile.service';
import { IUser } from 'app/underwriter/pages/model/user';
import { AppComponent } from "app/app.component";

@Component({
  selector: 'user-cmp',
  moduleId: module.id,
  templateUrl: './user.component.html'
})

export class UserComponent implements OnInit {
  public admin: IUser;
  public adminEmail = sessionStorage.getItem("underwriterEmail");

  constructor(private userProfileService: UserProfileService, private loginService: EmpLoginService, private openModalObj: AppComponent) { }

  ngOnInit(): void {
    this.getAdminData(this.adminEmail);
  }

  ngAfterViewInit(): void {
  }

  getAdminData(email): void {
    this.userProfileService.getAdminByEmail(email).subscribe(
      (response: any) => {
        // console.log(response);
        this.admin = response.data;
      },
      (error: HttpErrorResponse) => {
        // alert(error.message);
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal(error.message, "OK");
      }
    );
  }

  editAdminData(admin: IUser): void {
    this.userProfileService.updateAdminProfile(admin).subscribe(
      (response: any) => {
        // console.log(response.data);
        this.openModalObj.openCustomModal("Profile Updated uccessfully","OK");
      },
      (error: HttpErrorResponse) => {
        this.openModalObj.openCustomModal(error.message, "OK");
      }
    );
  }
}
