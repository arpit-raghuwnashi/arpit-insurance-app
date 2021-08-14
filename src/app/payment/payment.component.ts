import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PopupService } from '@ng-bootstrap/ng-bootstrap/util/popup';
import { OrderPolicesService } from 'app/services/order-polices.service';
import { PaymentService } from 'app/services/payment.service';
import { AppComponent } from 'app/app.component';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  orderId:any;
  amount:any;
  invoiceData:any;
  user:any="";
  customerPolicyDetails:any ="";
  product:any="";
  nominee:any="";
  dependent:any="";
  

  constructor(private _paymentService:PaymentService,
    private _service:OrderPolicesService,
    private route:ActivatedRoute,
    private router:Router,
    private modalService: NgbModal,
    private openModalObj: AppComponent) { }

  ngOnInit(): void {
    this.orderId = this.route.snapshot.paramMap.get('id') || "";
    this.amount = parseInt(localStorage.getItem('premium'));
    this.amount = Math.floor(this.amount);
    this.DisableBackButtonAllBrowsers();
   
  }

  payAmount(invoice:any){
      this._paymentService.payAmount(this.amount, this.orderId).subscribe(
        data=> {
            if(data.code==200)
            {
              this.getInvoice();
              console.log(this.amount, this.orderId);
              this.openModalObj.openCustomModal("Amount Paid Successfully","OK");
              localStorage.removeItem('premium');
              this.open(invoice);
            }else{
              // alert("Something went Wrong at server side");
              this.openModalObj.openCustomModal("Something went Wrong at server side","OK");
            }
        }
      )
  }

  appCustomerDetails = JSON.parse(localStorage.getItem('customerPolicyOrder'));
  appNominee = JSON.parse(localStorage.getItem('nominee'));
  appDependents = JSON.parse(localStorage.getItem('dependent'));

  submitApplication(invoice:any){
    this.appCustomerDetails.amountPaid=this.amount; 
   this._service.submitApplication
      (
        this.appCustomerDetails,
        this.appNominee,
        this.appDependents
      ).subscribe(
        result => {
          console.log(result)
          if(result.code==200)
          {
              this.orderId=result.data.orderId;
              this.getInvoice();
              localStorage.removeItem('nominee');
              localStorage.removeItem('customerPolicyOrder');
              localStorage.removeItem('dependent');
              localStorage.removeItem('premium');
              this.openModalObj.openCustomModal("Amount Paid Successfully","OK");
              this.open(invoice);
          } 
        }
      )
  }

  getInvoice(){
    this._paymentService.getInvoice(this.orderId).subscribe(
      data=> {
          if(data.code==200)
          {
            console.log(this.orderId);
            this.invoiceData = data.data
            this.user = data.data.user;
            this.customerPolicyDetails = data.data.customerPolicyDetails;
            this.product = data.data.product;
            this.nominee = data.data.nominee;
            console.log(this.nominee);
            this.dependent = data.data.dependents;
          }else{
            // alert("Something went Wrong at server side");
            this.openModalObj.openCustomModal("Something went Wrong at server side","OK");
          }
      }
    )
}

  activeModel:any;
  closeResult:any;

  DisableBackButtonAllBrowsers() {
    window.history.forward();
  };

  open(invoice:any) {
    this.activeModel =this.modalService.open(invoice, {ariaLabelledBy: 'modal-basic-title'})
    // this.activeModel.componentInstance.user = this.user;
    this.activeModel.result.then((result:any) => {
      this.router.navigate(['/userprofile-component']);
      this.closeResult = `Closed with: ${result}`;
    }, (reason:any) => {
      this.router.navigate(['/userprofile-component']);
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

  indianRupeeFormat(val: number) {
    return Number(val).toLocaleString('en-IN');
  }
  



  //onPrint(){
    
  //  window.print();
 // }

}
