import { Component, OnInit } from '@angular/core';
import { interval } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {

  modalbody: any;
  modalfooter: any;

  ngOnInit() {
    // checking every minute use is greater then 30 minutes of not
    interval(10000).subscribe(() => {
      const currentTime = new Date().getTime();
      const loginDate = sessionStorage.getItem("userDate");
      var loginTime: number = +loginDate;
      if(currentTime>loginTime && loginTime!=0 && localStorage.getItem('token')!=null){
        sessionStorage.clear();
        localStorage.clear();
        // alert("Your session has been expire. Pls login again");
        this.openCustomModal("Your session has been expire. Pls login again","OK");
        location.href='';
      }

      if(currentTime>loginTime && localStorage.getItem('token')!=null){
        sessionStorage.clear();
        localStorage.clear();
        this.openCustomModal("Your session has been expire. Pls login again","OK");
        location.href='';
      }

    });
  }

  openCustomModal(modalbody: any, modalfooter: any) {
    this.modalbody = modalbody;
    this.modalfooter = modalfooter;
    document.getElementById("openMsgModal").click();
  }

}
