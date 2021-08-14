import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { LoginService } from '../services/login.service';
import { User } from '../model/user';
import { Router } from '@angular/router';
import { EmpLoginService } from 'app/emp-login/emp-login.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credentials = {
    username: '',
    password: ''
  }

  errorMessage:string='';

  constructor(private loginService: EmpLoginService, private router: Router) {
  }

  ngOnInit(): void {
  }

  loginUser() {
    if (this.credentials.username != "" && this.credentials.password != "" && this.credentials.username != null && this.credentials.password != null) {
      console.log("From submitted");
      this.loginService.generateToken(this.credentials).subscribe(
        (response: any) => {
          // console.log(response.data[1]);
          if (response.data[0] == 3) {
            this.loginService.loginUser(response.data[1]);
            localStorage.setItem('userEmail', this.credentials.username);
            localStorage.setItem('userId', response.data[2].userId);

            // set login time of user in session stroage
            const now = new Date().getTime() + (30 * 60 * 1000);
            sessionStorage.setItem('userDate', String(now));
            window.location.href = "/userprofile-component";
          } else {
           this.errorMessage="Invailid Username and Password";
           this.credentials.password=' '; 
          }
        },
        error => {
          console.log(error);
          this.errorMessage="Invailid Username and Password";
          this.credentials.password=' ';
        });
    } else {
      console.warn("Empty values");
    }

  }

}
