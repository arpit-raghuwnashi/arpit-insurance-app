import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute,Router } from '@angular/router'
import { UserProfileService } from '../services/user-profile.service';
import { AppComponent } from 'app/app.component';

@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.css']
})
export class UserprofileComponent implements OnInit {
userId:any;
policies:any;
customerPolicyDetails:any;
user:any
policyDatas:any;
// userId:any;
closeResult = '';

  constructor(
    private router:Router, private _service:UserProfileService,
    private httpClient: HttpClient,
    private modalService: NgbModal,
    private fb: FormBuilder,
    private openModalObj: AppComponent
  ) { }

  ngOnInit(): void {
    this.userId = localStorage.getItem('userId')
    // if(!this.userId)
    // {
    //   // location.reload()
    //   this.router.navigate(['']);
    // }else{
      this.getUserDetails();
    // }
  }

  activeModel:any;
  reviewData:any;

  open(content:any,id:any) {
    console.log("i============",this.policyDatas[id])
    this.activeModel =this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'})
    // this.activeModel.componentInstance.reviewData = this.policyDatas[id];
    localStorage.setItem('premium',this.policyDatas[id].premiumAmount);
    this.reviewData = this.policyDatas[id];
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
  
  getUserDetails(){
    // console.log("----------------------",this.userId)
    this._service.getUserData(this.userId).subscribe(
      result=>{
        if(result.code==200)
        {
          console.log("-----------------",result.data)
          this.policies=result.data.product;
          this.customerPolicyDetails = result.data.customerPolicyDetails;
          this.user = result.data.user;
           this.policyDatas = this.customerPolicyDetails.map((item:any, index:any) => ({ ...item, ...this.policies[index] }));
           console.log("--------------------",this.policyDatas)
        }else{
          // alert("something went wrong");
          this.openModalObj.openCustomModal("Something went Wrong","OK");
        }
      }
    );
  }

  indianRupeeFormat(val: number) {
    return Number(val).toLocaleString('en-IN');
  }

}



