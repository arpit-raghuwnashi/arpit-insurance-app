import { Component, OnInit, NgModule } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { CustomerPolicyOrder } from '../model/customer-policy-order';
import { Dependent } from '../model/dependent';
import { Nominee } from '../model/nominee';
import { OrderPolicesService } from '../services/order-polices.service';
import { PoliciesService } from '../services/policies.service';
import { FormGroup, FormControl,FormArray, FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms'
import { Common } from '../model/common';
import { from } from 'rxjs';
import { AppComponent } from "../app.component";

@Component({
  selector: 'app-order-polices',
  templateUrl: './order-polices.component.html',
  styleUrls: ['./order-polices.component.css']
})
export class OrderPolicesComponent implements OnInit {

  id=""
  sumAssured:any=0;
  minNumOfDependent:Number =0;
  maxNumOfDependent:Number=0;
  numberOfYears:any=0; 
  minAgeLimit:any=0;
  maxAgeLimit:any=0;
  userEmail=localStorage.getItem('userEmail') || "";
  userDob:any;
  userAge:any;
  today:any;
  premium:any=0;
  basicPremium:any=0;
  numberOfDependent:any=1;
  paymentFrequency:any='null';
  policy_id:any;
  product_id:any;
  user_id:any;
  resData="";
  status:any;
  step:any=1;
  // DependentForm:Dependent;
 firstName:any;
 lastName:any;
 email:any;
 phone:any;
 age:any; 
 nominee = new Nominee("","","","","");
 dependent:any = [];
 common:Common = new Common([],[],[],[],"",[])
 count:any=0;
 policyDependent:FormGroup;
 dependentForm:FormGroup;
 dependentObject:any = {}
 dependentArray:Dependent[] = []
 customerPolicyOrder = new CustomerPolicyOrder("","","","","","")
//  maxNumOfDependent: any;

 //for data toggle
// seeMore:boolean = true;
show = false
// dataToggleBtn(){
  // this.seeMore=!this.seeMore;
 //}

  constructor(private route: ActivatedRoute, private location:Location, private _service:OrderPolicesService, private router:Router,private policyservice:PoliciesService,private fb:FormBuilder, private pd:FormBuilder,
    private openModalObj: AppComponent) {

  //  this.policyDependent = this.pd.group({
//      numberOfDependent: [],
     // paymentFrequency: [],
   // })    
    
    this.dependentForm = this.fb.group({
        firstName:['',[Validators.required, Validators.minLength(2),Validators.maxLength(50),Validators.pattern("^[a-zA-z]*$")]],
        lastName:['', [Validators.required, Validators.minLength(2),Validators.maxLength(50),Validators.pattern("^[a-zA-z]*$")]],
        middleName:['',[Validators.minLength(2),Validators.maxLength(50),Validators.pattern("^[a-zA-z]*$")]],
        email:['',[Validators.required, Validators.minLength(2),Validators.maxLength(50),Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")]],
        phone:['',[Validators.required, Validators.minLength(2),Validators.maxLength(10),Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]],
        age:['',[Validators.required, Validators.minLength(1),Validators.maxLength(2),Validators.pattern("^((\\+91-?)|0)?[0-9]{0,2}$")]],
        gender:['',Validators.required]
    });

   }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id') || "";
    this.getPolicyById(parseInt(this.id));
    this.getUserByEmail(this.userEmail);
    //this.calculatePremium();
    localStorage.removeItem('customerPolicyOrder');
    localStorage.removeItem('nominee');
    localStorage.removeItem('dependent');
  }


   getFirst(){ 
      this.dependent = this.dependent.concat(this.dependentForm.value);
      this.dependentForm = this.fb.group({
        firstName:[],
        lastName:[],
        middleName:[],
        email:[],
        phone:[],
        age:[],
        gender:[]
    });
    
      // this.dependent.dependentForm.userId=this.user_id;
      this.count=this.count+1;
      this.step=this.step+1;
      
      console.log(this.dependent);
      console.log("from get---------"+this.user_id);
   }



 cancel(){
  this.location.back();
  // this.router.navigate(['/order-polices/'+this.product_id]);
 }

 previous(){
   this.step=this.step-1;
   console.log(this.step);
 } 

 next(){
  this.step=this.step+1;
  console.log("from next======="+this.step);
}


  getPolicyById(id:Number)
  {
    this._service.getPolicyByIdFromRemote(this.id).subscribe(
      result => {
        console.log(result)
        if(result.code == 200)
        {
          this.sumAssured = result.data.sumAssured;
          this.minNumOfDependent = result.data.minNumDependents;
          this.maxNumOfDependent = result.data.maxNumDependents;
          this.numberOfYears = result.data.numberOfYearsCovered;
          this.minAgeLimit = result.data.minAgeLimit;
          this.maxAgeLimit = result.data.maxAgeLimit;
          this.policy_id = result.data.policyId;
          this.product_id = result.data.productId;
        }else{
          // alert("policies are not fetched");
          this.openModalObj.openCustomModal("Policies are not fetched","OK");
        }
        // this.ngOnInit();
      } 
    )
  }

  getUserByEmail(email:String)
  {
    this._service.getUserByEmail(this.userEmail).subscribe(
      result => {
        console.log(result)
        if(result.code == 200)
        {
          this.userDob = result.data.dateOfBirth;
          this.today = new Date();
          this.userDob =new Date(this.userDob)
          this.userAge = ((this.today - this.userDob) / (31557600000));
          this.userAge = Math.floor(this.userAge)
          this.user_id = result.data.userId;
        }else{
          // alert("User not fetched By Emails");
          this.openModalObj.openCustomModal("User not fetched by Email","OK");
        }
        // this.ngOnInit();
      } 
    )
  }

  // basic_premium_permonth(for min_age) = (sum_assured)*(num_dependents+1)/(12*number_of_years*payment_ferquency)

  calculatePremium(){
      // this.numberOfDependent=this.numberOfDependent;
      console.log("sum Assured=====",this.sumAssured);
      console.log("number of dependents",this.numberOfDependent);
      console.log("number of years",this.numberOfYears);
      console.log("age",this.userAge-this.minAgeLimit);
      console.log(this.userAge);
      console.log(this.minAgeLimit);
      console.log("basic premiunm",this.basicPremium);
      this.basicPremium = (this.sumAssured)*10*(this.numberOfDependent+1)/(12*this.numberOfYears*12);
      this.premium = (this.userAge-this.minAgeLimit)*100+(0.05*this.basicPremium);
      console.log(this.premium);
    if(this.paymentFrequency=='Monthly')
    {
      this.premium = this.premium*1;
    }
    else if (this.paymentFrequency=='Quaterly') {
      this.premium = this.premium*3;
    } else if (this.paymentFrequency=='HalfYearly'){
      this.premium = this.premium*6;
    }else if(this.paymentFrequency=='Yearly'){
      this.premium=this.premium*12;
    } 
  }



  submitApplication(){

    this.dependentObject.dependent = this.dependent
    // console.log(this.dependentObject);
    // console.log(this.nominee);
    // console.log(this.customerPolicyOrder);
    this.nominee.userId=this.user_id;
    this.customerPolicyOrder.productId = this.product_id;
    this.customerPolicyOrder.policyId  =this.policy_id;
    this.customerPolicyOrder.userId  = this.user_id;
    this.customerPolicyOrder.numberOfDependent  = this.numberOfDependent;
    this.customerPolicyOrder.paymentFrequency  = this.paymentFrequency;
    this.customerPolicyOrder.premiumAmount  = this.premium;
    localStorage.setItem('premium',this.premium);
    localStorage.setItem('customerPolicyOrder',JSON.stringify(this.customerPolicyOrder));
    localStorage.setItem('nominee',JSON.stringify(this.nominee));
    localStorage.setItem('dependent',JSON.stringify(this.dependent));
    this._service.checkPolicyApproval(this.customerPolicyOrder).subscribe(
      result => {
              console.log(result)
              if(result.code==200)
              {
                this.resData = result.message;
                this.status = result.data.status;
                if(this.status=="Pending")
                {
                  this.sendForApproval(this.customerPolicyOrder, this.nominee,this.dependent);
                }else{
                  this.router.navigate(['/payment/']);
                }
              } 
            }
    )
  }
  sendForApproval(customerPolicyOrder:CustomerPolicyOrder, nominee:Nominee,dependent:Dependent[]){
    this._service.submitApplication
      (
        customerPolicyOrder,
        nominee,
        dependent
      ).subscribe(
        result => {
          console.log(result)
          if(result.code==200)
          {
                this.status = result.data.status;
                this.openModalObj.openCustomModal("Your Policy is "+this.status,"OK");

                this.location.back();
          }
        }
      )
  }
}
