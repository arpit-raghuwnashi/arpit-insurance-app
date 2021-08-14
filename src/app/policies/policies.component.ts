import { Component, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PoliciesService } from '../services/policies.service';
import { Location } from '@angular/common';
import {NgbModal, ModalDismissReasons, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import { AppComponent } from "app/app.component";

@Component({
  selector: 'app-policies',
  templateUrl: './policies.component.html',
  styleUrls: ['./policies.component.css']
})
export class PoliciesComponent implements OnInit {

  policiesData: any;
  product_name:String="";
  id:any;
  user_id:any;
  checkuserWithProduct:boolean=false;
  modelData="";
  closeResult: string="";
  modalOptions:NgbModalOptions;
  policyTitle: any;
  oneProductData:any;
  session:any;
  userAge:any;
  userDob:any;
  today:any;
  // console.log(this.policiesData[0])

  constructor(private route: ActivatedRoute, private _service:PoliciesService, private router:Router,private location:Location, private modalService: NgbModal, private openModalObj: AppComponent) { 
     
    this.modalOptions = {

      backdrop:'static',
      backdropClass:'customBackdrop'
    
    }

    this.route.paramMap.subscribe(params => {
        this.ngOnInit();
    });  
  }



  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id') || "";
    this.getPolicyById(parseInt(this.id));
    // this.getUserByEmail(this.session||"")
    this.session=localStorage.getItem('userEmail');
    console.log(this.id);
    this.user_id = parseInt(localStorage.getItem('userId') || "");
    // console.log("userId------------",this.user_id)
    if(this.session){
      this.getUserByEmail(this.session||"")
    }
    if (this.id==1) {
      this.policyTitle="Life Insurance";
      
    } else if(this.id==2) {
      this.policyTitle="Dental Insurance";
    } else {
      this.policyTitle="Eye Insurance";
    }
  }

  getPolicyById(id:Number)
  {
    console.log("Id====",this.id)
    this._service.getPolicyByIdFromRemote(this.id).subscribe(
      result => {
        // console.log(result)
        if(result.code == 200)
        {
          console.log("PolicyData====",result.data)
          this.policiesData = result.data;
        }else{
          // alert("policies are not fetched")
          this.openModalObj.openCustomModal("Policies are not fetched!","OK");
          
        }
        // this.ngOnInit();
      } 
    )
  }

getUserByEmail(email:String)
  {
    this._service.getUserByEmail(this.session || "").subscribe(
      result => {
        // console.log(result)
        if(result.code == 200)
        {
          this.user_id = result.data.userId;
          this.today = new Date();
          this.userDob =new Date(result.data.dateOfBirth)
          this.userAge = ((this.today - this.userDob) / (31557600000));
          // alert(this.userAge)
        }else{
          console.log("User not fetched By Emails")
        }
        // this.ngOnInit();
      } 
    )
  }

  checkAge(index):boolean{
    if(this.userAge>this.policiesData[index].minAgeLimit && this.userAge<this.policiesData[index].maxAgeLimit)
    {
      return true;
    }else{
      return false;
    }
  }

  checkUserWithProduct(productId:number, index:any){
    this._service.checkUserWithOrderFromRemote(this.user_id,productId).subscribe(
      result => {
        console.log("check is called"+result.message)
        if(result.code == 200)
        {
          // alert("You already have this Policy");
          this.openModalObj.openCustomModal("You already have this Policy","OK");
          this.location.back(); 
          // this.open()
        }else{
          if(this.checkAge(index))
          {
            this.router.navigate(['/order-polices/'+productId]);
          }else{
            alert("You are not following Age Conditions");
            this.location.back();
          }
        }
        // this.ngOnInit();
      } 
    )
  }

  redirect(product_id:any,index:any)
  {
    if(this.session){
      this.checkUserWithProduct(product_id,index);

      this.router.navigate(['/order-polices/'+product_id]);
    }else{
      // alert("You have to login First");
      this.openModalObj.openCustomModal("You have to login First","Sure");
      this.router.navigate(['/login-component']);
      // this.open() 
    }  
  }
  
  activeModel:any;
  close:any;

  open(policyPreview:any,id:any) {
    this.oneProductData=this.policiesData[id];
    this.activeModel =this.modalService.open(policyPreview, {ariaLabelledBy: 'modal-basic-title'})
    this.activeModel.result.then((result:any) => {
      this.close = `Closed with: ${result}`;
    }, (reason:any) => {
      this.close = `Dismissed ${this.getDismissReason(reason)}`;
    });
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

}
