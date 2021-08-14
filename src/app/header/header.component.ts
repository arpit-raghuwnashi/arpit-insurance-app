import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RegisterService } from '../services/register.service';
import { User } from '../model/user';
import { PoliciesService } from '../services/policies.service';
import { ClaimServiceService } from '../services/claim-service.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { AppComponent } from "../app.component";
import { Location } from '@angular/common';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  session: any;
  resData = "";
  user = new User("", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
  userId: any;
  nomineeId: any;
  orderId: any;
  token: any;
  nomineeName: any;
  customerName: any;
  nomineeRes:any="";
  userRes:any="";
  orderRes:any="";  
  verifyRes:any="";

  constructor(private activateRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private _service: RegisterService,
    private _policyService: PoliciesService,
    private _claimService: ClaimServiceService,
    private _snackBar: MatSnackBar,
    private location:Location,
    private openModalObj: AppComponent) { }

  ngOnInit(): void {
   this.session = localStorage.getItem('userId');
   if(this.session){
    this.fetchUserDetails(localStorage.getItem('userEmail'));
   }
   
  }

  logout(){
    localStorage.clear();
    location.reload();
    // this.router.navigate(['']);
  }

  activeModel:any;
  closeResult:any;

  open(contentUpdateProfile:any) {
    this.activeModel =this.modalService.open(contentUpdateProfile, { ariaLabelledBy: 'modal-basic-title' })
    // this.activeModel.componentInstance.user = this.user;
    this.activeModel.result.then((result:any) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason:any) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
    // this.activeModel.componentInstance.modelContent = this.policyDatas[content];
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  openClaim(claim:any) {
    this.activeModel =this.modalService.open(claim, {ariaLabelledBy: 'modal-basic-title'})
    // this.activeModel.componentInstance.user = this.user;
    this.activeModel.result.then((result:any) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason:any) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
    // this.activeModel.componentInstance.modelContent = this.policyDatas[content];
  }


  checkNominee(){
    this._claimService.checkNominee(this.nomineeId).subscribe(
        data=>{
          if(data.code!=200)
          {
            this.nomineeRes=data.message; 
          }else{
            this.nomineeRes=""
          }
        }
    )
  }

  checkUser(){
    this._claimService.checkUser(this.userId).subscribe(
        data=>{
          if(data.code!=200)
          {
            this.userRes=data.message; 
          }else{
            this.userRes=""
          }
        }
    )
  }

  checkOrder(){
    console.log("inside checkUser");
    this._claimService.checkOrder(this.orderId).subscribe(
        data=>{
          console.log("inside checkUser data");
          if(data.code!=200)
          {
            this.orderRes=data.message; 
          }else{
            this.orderRes=""
          }
        }
    )
  }

  verifyClaim(){
    console.log("Inside verify Claim")
    this._claimService.claimDetailVerification(this.nomineeName, this.customerName,this.userId, this.nomineeId, this.orderId).subscribe(
        data=>{
          console.log(data);
          if(data.code==200)
          {
            this.verifyRes="";
            this.claimApplication();
          }else{
            this.verifyRes="Your details are wrong";
          }
        }
    )
  }

  claimApplication() {
    this._claimService.claimApplication(this.nomineeName, this.customerName, this.userId, this.nomineeId, this.orderId).subscribe(
      data => {
        if (data.code) {
          document.getElementById("closeclaimmodal").click();
          // this.openSnackBar("Your claim is saved successfully. Our team will contact you soon!", "OK");
          this.openModalObj.openCustomModal("Your claim is saved successfully. Our team will contact you soon!", "OK");
        } else {
          document.getElementById("closeclaimmodal").click();
          // this.openSnackBar("Your Claim is Invalid! Our team will contact you soon.", "Close");
          this.openModalObj.openCustomModal("Your Claim is Invalid! Our team will contact you soon.", "OK");
        }
      }
    )
  }

  updateApplication(){
    this._service.updateUserFromRemote(this.user).subscribe(
      data => {
        console.log(data)
        if (data.code == 200) {
          this.resData = "";
          // alert("User details updated succesfully");
          this.openModalObj.openCustomModal("User details Updated Succesfully","OK");
          window.location.href = "/userprofile-component";
        } else {
          this.resData = data.message;
          // alert("wrong Credentials")
        }
      } 
    )
  }


  fetchUserDetails(email:any){
    this._policyService.getUserByEmail(email).subscribe(
      data => {
        console.log(data)
        if(data.code == 200)
        {
          this.user = data.data;
          console.log("data---------",data);
        }else{
          this.resData= data.message;
          // alert("wrong Credentials")
        }
      } 
    )
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      // duration: 5000,
      verticalPosition: "top"
    });
  }
}
