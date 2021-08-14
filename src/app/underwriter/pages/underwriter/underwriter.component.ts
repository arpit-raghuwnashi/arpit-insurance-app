import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AppComponent } from 'app/app.component';
import { EmpLoginService } from 'app/emp-login/emp-login.service';
import { IUser } from '../model/user';
import { UnderwriterService } from '../services/underwriter.service';

@Component({
  selector: 'app-underwriter',
  templateUrl: './underwriter.component.html',
  styleUrls: ['./underwriter.component.css']
})
export class UnderwriterComponent implements OnInit, AfterViewInit {
  public underwriter: IUser;
  public underwriterEmail = sessionStorage.getItem("underwriterEmail");

  constructor(private underwriterService: UnderwriterService, private loginService: EmpLoginService, private openModalObj: AppComponent) { }

  ngOnInit(): void {
    this.getUnderWriterData(this.underwriterEmail);
  }
  ngAfterViewInit(): void {
  }

  //retreive underwriter data
  getUnderWriterData(email): void {
    this.underwriterService.getUnderWriter(email).subscribe(
      (response: any) => {
        this.underwriter = response.data;
      }, (error: HttpErrorResponse) => {
        // alert(error.message);
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal(error.message, "OK");
      }
    );
  }

  // update underwriter data
  editUnderwriterData(underwriter: IUser): void {
    console.log(underwriter.aadharNo);
    this.underwriterService.updateUnderWriter(underwriter).subscribe(
      (response: any) => {
         this.openModalObj.openCustomModal("Data Updated Successfully", "OK");
      }, (error: HttpErrorResponse) => {
        // alert(error.message);
        this.openModalObj.openCustomModal(error.error.message, "OK");
      }
    );
  }

  // deactive underwriter
  deactiveUnderwriter(underwriterId) {
    this.underwriterService.deactiveUnderwriter(underwriterId).subscribe(
      data => {
        this.loginService.logout();
        window.location.href = "";
      }, error => {
        // alert(error.error.message);
        this.openModalObj.openCustomModal(error.error.message, "OK");
      }
    );

  }
}
