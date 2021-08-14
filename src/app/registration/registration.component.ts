import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { from } from 'rxjs';
import { User } from '../model/user';
import { RegisterService } from '../services/register.service';
import { AppComponent } from "app/app.component";
// import { bcrypt } from 'bcryptjs';
// import {
//   MatSnackBar,
//   MatSnackBarHorizontalPosition,
//   MatSnackBarVerticalPosition,
// } from '@angular/material/snack-bar';


@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  step: any = 1;
  confirmPassword: string;
  resData = ""
  user = new User("","","","","","","","","","","","","","","");
  constructor(private _service:RegisterService, private router:Router, private openModalObj: AppComponent) {

   }

  //  openSnackBar() {
  //   this._snackBar.open('Cannonball!!', 'End now', {
  //     duration: 500,
  //     horizontalPosition: this.horizontalPosition,
  //     verticalPosition: this.verticalPosition,
  //   });
  // }

next(){
  this.step = this.step + 1;
  if(this.step == 4){

  }
}
  
  ngOnInit() :void {
  }

  

  submitApplication(){
    // this.user.password = bcrypt.hashpw(this.user.password, bcrypt.gensalt());
    this._service.registerUserFromRemote(this.user).subscribe(
      data => {
        console.log(data)
        if(data.code == 200)
        {
          this.resData="";
          // alert("Registered succesfully")
          this.openModalObj.openCustomModal("Registered succesfully","OK");
          this.router.navigate(['/login-component']);
        }else{
          this.resData= data.message;
          // alert("wrong Credentials");
          this.openModalObj.openCustomModal("Can't Register! User already exists","OK");
        }
      }
      
    )
   
  }

  previous(){
    this.step = this.step - 1;
  }

}
