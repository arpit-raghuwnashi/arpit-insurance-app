import { Component, OnInit } from '@angular/core';
import { from } from 'rxjs';
import { FormsModule } from "@angular/forms";
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ContactUsService } from "../services/contact-us.service";
import { AppComponent } from 'app/app.component';

@Component({
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {
  name:any;
  email:any;
  phone:any;
  message:any;
  // enquirer : Enquirer;
  // submitted : boolean = false;
  
  constructor(private contact_service:ContactUsService, private openModalObj: AppComponent ) { }

  ngOnInit(): void {
  }

  submitForm(){
    this.contact_service.contactUS(this.name, this.email, this.phone, this.message).subscribe(
      data => {
        if (data.code==200){
          //alert("mail has been sent");
          this.openModalObj.openCustomModal("Mail has been sent","OK");
        }
      }
    )
  
   // this.submitContact(this.enquirer);

  }
}
