import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { User } from 'app/model/user';
import { OrderDetails } from '../model/orderDetails';
import { PolicyResponse } from '../model/policyStatusResponse';
import { UnderwriterService } from '../services/underwriter.service';
import { AppComponent } from "app/app.component";

@Component({
  selector: 'app-decline-list',
  templateUrl: './decline-list.component.html',
  styleUrls: ['./decline-list.component.css']
})
export class DeclineListComponent implements OnInit {

  public underwriter:User;
  public orderDetails:OrderDetails[];
  public underwriterEmail:string;
  public orderId:number;
  public message:string;

  constructor(private underwriterService:UnderwriterService, private openModalObj: AppComponent) { }

  pendingStatusResponse={
    orderId:0,
    status:'',
    message:''
  }

  ngOnInit(): void {
    this.underwriterEmail=sessionStorage.getItem("underwriterEmail");;
    this.getUnderWriterData(this.underwriterEmail);
    this.getDeclineOrderPolicies();
    
  }

  // retrieving underwriter data
  getUnderWriterData(email):void{
    this.underwriterService.getUnderWriter(email).subscribe(
      (response:any) =>{
        this.underwriter=response.data;
      },(error:HttpErrorResponse) =>{
        // alert(error.message);
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal(error.message,"OK");
      }
    );
  }

  // retrieving decline policies
  getDeclineOrderPolicies():void{
    this.underwriterService.getDeclineCustomerPolicyDetails().subscribe(
      (response:any)=>{
        this.orderDetails=response.data;
      },
      (error:HttpErrorResponse)=>{
        // alert(error.message);
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal(error.message,"OK");
      });
  }

  // update status of policy to APPROVED/DECLINE
   updatePolicyStatus(status,orderId):void{
    // this.pendingStatusResponse.email=this.underwriterEmail;
    this.pendingStatusResponse.status=status;
    this.pendingStatusResponse.orderId=orderId;
    this.pendingStatusResponse.message=this.message;

    this.underwriterService.updatePendingStatus(this.pendingStatusResponse).subscribe(
      (response: any) => {
        location.reload();
      }, (error: HttpErrorResponse) => {
        // console.log(error);
        this.openModalObj.openCustomModal(error.message,"OK");
      });
      this.ngOnInit();
   }

   // assigining order id to variable
   public getOrderId(orderId:number){
    this.orderId=orderId;
   }

   indianRupeeFormat(val: number) {
    return Number(val).toLocaleString('en-IN');
  }

}
