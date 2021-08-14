import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EmpLoginService } from 'app/emp-login/emp-login.service';
import { ForgotService } from './forgot.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent implements OnInit {


  email: string = '';
  OTP: number;

  public successMessage: string;
  public errorMessage: string;
  public showSuccess: boolean;

  // hiding one component 
  public forgotPasswordFormDisplay:boolean=true;
  public newPasswordFormDisplay:boolean=false;

  constructor(private forgotService: ForgotService,private loginService: EmpLoginService) { }

  ngOnInit(): void {

  }
  // hide OTP field
  public showOptField(){
    return this.successMessage=="Otp has been sent successfully to your mail";
  }

  // recieving both email and otp and calling related method 
  forgotPassword() {
    if (this.email != '' && this.email != null && this.OTP == null) {
      this.successMessage = "Waiting ! we are sendinng you mail.";
      this.showSuccess = true;
      this.sentOtp();
    } 

    if (this.OTP != null) {
      this.verifyOtp();
    }
  }

  // Sending otp 
  sentOtp() {
    this.forgotService.sendOtpFromRemote(this.email).subscribe(
      (response: any) => {
        this.successMessage = response.message;
        console.log(response.message);
        if (this.successMessage != "") {
          this.showSuccess = true;
        } else {
          this.showSuccess = false;
        }
      },
      (error: HttpErrorResponse) => {
        this.errorMessage = error.error.message;
        console.error(error.error.message);
      });
  }


  // verifying otp from remote
  verifyOtp() {
    this.forgotService.verifyOtpFromRemote(this.OTP).subscribe(
      (response: any) => {
        this.successMessage = response.message;
        this.showSuccess = true;
        console.log(response.message);

        this.forgotPasswordFormDisplay=false;
        this.newPasswordFormDisplay=true;
        this.userEmail=this.email;
        this.optMessage=true;
      },
      (error: HttpErrorResponse) => {
        this.successMessage = '';
        this.errorMessage = error.error.message;
        console.error(error);
      }
    );
  }

  // new password 
  userEmail: string;
  newPassword: string;
  confirmPassword: string;

  credentials = {
    email: '',
    password: ''
  }

  //error message
  newErrorMessage: string;
  showError: boolean;
  newSuccessMessage: string;
  newShowSuccess: boolean;
  loginMessage: boolean;
  optMessage:boolean;

// updating user password
public onSubmit() {
  if (this.newPassword != '' && this.newPassword != null && this.confirmPassword != '' && this.confirmPassword != null) {
    if (this.newPassword == this.confirmPassword) {
      this.credentials.email = this.userEmail;
      this.credentials.password = this.newPassword;

      this.forgotService.resetPasswrod(this.credentials).subscribe(
        (response: any) => {
          this.successMessage = response.message;
          console.log(response.message);
          this.userLocation(response.data);
        },
        (error: HttpErrorResponse) => {
          this.errorMessage = error.error.message;
          this.showError = true;
          console.error(error);
        }
      );
    } else {
      this.errorMessage = "Different! Password must be same.";
      this.showError = true;
    }
  } else {
    this.errorMessage = "Fields should not be empty.";
    this.showError = true;
  }
}

// sending user to related page
public userLocation(roleId: number) {
  if (this.loginService.isLoggedIn()) {
    switch (roleId) {
      case 1: { location.href = "/admin/dashboard"; break; }

      case 2: { location.href = "/underwriter/dashboard"; break; }

      case 3: { location.href = '/userprofile-component'; break; }
    }
  } else {
    this.optMessage=false;
    this.loginMessage = true;
    this.newPassword=' ';
    this.confirmPassword=' ';
  }
}

}
