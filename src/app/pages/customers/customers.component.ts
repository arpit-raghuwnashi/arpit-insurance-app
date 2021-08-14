import { Component, OnInit } from "@angular/core";
import { ICustomer } from "./customers";
import { CustomersService } from "./customers.service";
import { HttpErrorResponse } from "@angular/common/http";
import { AppComponent } from "app/app.component";

@Component({
  selector: "customers-cmp",
  moduleId: module.id,
  templateUrl: "./customers.component.html",
})
export class CustomersComponent implements OnInit {
  constructor(private service: CustomersService, private openModalObj: AppComponent) {}

  page = 1;
  pageSize = 4;
  collectionSize: number;
  customers: ICustomer[];
  allCustomers: ICustomer[];

  ngOnInit(): void {
    this.service.fetchCustomers().subscribe(
      (response: any) => {
        console.log(response);
        this.collectionSize = response.data.length;
        this.customers = response.data;
        this.allCustomers = this.customers;
      },
      (error: HttpErrorResponse) => 
      {
        console.log(error.message);
        // alert("Internal Server Error");
        this.openModalObj.openCustomModal("Internal Server Error", "OK");
      }
    );
  }

  search(value: string): void {
    this.customers = this.allCustomers.filter(
      (val) =>
        val.email.toLowerCase().includes(value) ||
        val.firstName.toLowerCase().includes(value) ||
        val.lastName.toLowerCase().includes(value) ||
        val.userId.toString().includes(value) ||
        val.roleId.toString().includes(value) ||
        val.policyCount.toString().includes(value) 
        
    );
    this.collectionSize = this.customers.length;
  }
  public dependents = [];
  fetchDependents(policy: any) {
    this.service.getDependents(policy.orderId).subscribe(
      (response: any) => {
        console.log(response);
        this.dependents = response.data;
      },
      (error: HttpErrorResponse) => 
      {
        console.log(error.message);
        // alert("Internal Server Error");
        this.openModalObj.openCustomModal("Internal Server Error", "OK");
      }
    );
  }

  public nominees = [];
  fetchNominee(policy: any)
  {
    this.service.getNominee(policy.orderId).subscribe(
    (response: any) => 
    {
      console.log(response);
      this.nominees = response.data;
    },
    (error: HttpErrorResponse) => 
    {
      console.log(error.message);
      // alert("Internal Server Error");
      this.openModalObj.openCustomModal("Internal Server Error", "OK");
    });
  }

  public policyDetails = [];
  fetchCustomerPolicyDetails(customer: any) 
  {
    this.service.getCustomerPolicyDetails(customer.userId).subscribe(
      (response: any) => 
      {
        console.log(response);
        this.policyDetails = response.data;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
        // alert("Internal Server Error");
        this.openModalObj.openCustomModal("Internal Server Error", "OK");
      }
    );
  }

  public products: any;
  fetchP(policy: any) 
  {
    this.service.getP(policy.productId).subscribe(
      (response: any) => 
      {
        console.log(response);
        this.products = response.data;
      },
      (error: HttpErrorResponse) => 
      {
        console.log(error.message);
        // alert("Internal Server Error");
        this.openModalObj.openCustomModal("Internal Server Error", "OK");
      }
    );
  }

  indianRupeeFormat(val: number) {
    return Number(val).toLocaleString('en-IN');
  }
}
