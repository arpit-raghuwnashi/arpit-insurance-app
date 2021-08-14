import { Component, OnInit } from '@angular/core';
import { EmpLoginService } from './emp-login.service';

@Component({
  selector: 'app-emp-login',
  templateUrl: './emp-login.component.html',
  styleUrls: ['./emp-login.component.css']
})
export class EmpLoginComponent implements OnInit {

  public errorMessage: string = '';

  credentials = {
    username: '',
    password: ''
  }
  constructor(private loginService: EmpLoginService) { }

  ngOnInit(): void {
  }

  onLoginSubmit() {
    if (this.credentials.username != "" && this.credentials.password != "" && this.credentials.username != null && this.credentials.password != null) {
      console.log("From submitted");
      this.loginService.generateToken(this.credentials).subscribe(
        (response: any) => {
          if (response.data[0] != 3) {
            this.loginService.loginUser(response.data[1]);
            // assign undername to dashboard vairiable

            //set user email in sesssion stroage
            sessionStorage.setItem('underwriterEmail', this.credentials.username);

            // set login time of user in session stroage
            const now = new Date().getTime() + (30 * 60 * 1000);
            sessionStorage.setItem('userDate', String(now));
            let role = response.data[0];
            if (role == 1) {
              window.location.href = "/admin/dashboard";
            } else {
              window.location.href = "/underwriter/dashboard";
            }
          }else{
            this.errorMessage="Invalid Username and Password";
            this.credentials.password=' ';
          }
        },
        error => {
          this.errorMessage = "Invalid Username and Password.";
          // console.log(error);
          this.credentials.password=' ';
        }
      );
    } else {
      this.errorMessage = "Field should not be empty";
    }
  }

}
