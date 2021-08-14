import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'app/app.component';
import { User } from 'app/model/user';
import { OrderDetails } from '../model/orderDetails';
import { PolicyResponse } from '../model/policyStatusResponse';
import { UnderwriterService } from '../services/underwriter.service';

@Component({
  selector: 'app-pending-list',
  templateUrl: './pending-list.component.html',
  styleUrls: ['./pending-list.component.css']
})
export class PendingListComponent implements OnInit {

  public underwriter: User;
  public orderDetails: OrderDetails[];
  public underwriterEmail:string;
  public orderId: number;
  public messsage: string;
  // public pendingStatusResponse : PolicyResponse;

  constructor(private underwriterService: UnderwriterService, private openModalObj: AppComponent) { }

  pendingStatusResponse={
    email:'',
    orderId:0,
    status:'',
    message:''
  }

  ngOnInit(): void {
    this.underwriterEmail=sessionStorage.getItem("underwriterEmail");
    this.getUnderWriterData(this.underwriterEmail);
    this.getPendingOrderPolicies();

  }

  // retrieving underwriter data
  getUnderWriterData(email): void {
    this.underwriterService.getUnderWriter(email).subscribe(
      (response: any) => {
        this.underwriter = response.data;
      }, (error: HttpErrorResponse) => {
        // alert(error.message);
        this.openModalObj.openCustomModal(error.message,"OK");
      }
    );
  }

  // retrieving pending policies
  getPendingOrderPolicies(): void {
    this.underwriterService.getPendingCustomerPolicyDetails().subscribe(
      (response: any) => {
        this.orderDetails = response.data;
      },
      (error: HttpErrorResponse) => {
        // alert(error.message);
        this.openModalObj.openCustomModal(error.message,"OK");
      });
  }

  // update status of policy to APPROVED/DECLINE
  updatePolicyStatus(status, orderId): void {
    this.pendingStatusResponse.email=this.underwriterEmail;
    this.pendingStatusResponse.status=status;
    this.pendingStatusResponse.orderId=orderId;
    this.pendingStatusResponse.message=this.messsage;

    this.underwriterService.updatePendingStatus(this.pendingStatusResponse).subscribe(
      (response: any) => {
        location.reload();
      }, (error: HttpErrorResponse) => {
        // console.log(error);
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal(error.message,"OK");
      });
  }

  // assigining order id to variable
  public getOrderId(orderId) {
    this.orderId = orderId;
  }

  indianRupeeFormat(val: number) {
    return Number(val).toLocaleString('en-IN');
  }

}
