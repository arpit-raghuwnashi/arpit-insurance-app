import { HttpErrorResponse } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { IClaim } from "./claims";
import { ClaimsService } from "./claims.service";
import { AppComponent } from "app/app.component";

@Component({
  selector: "claims-cmp",
  moduleId: module.id,
  templateUrl: "./claims.component.html",
})
export class ClaimsComponent implements OnInit {
  constructor(private service: ClaimsService, private openModalObj: AppComponent) { }
  page = 1;
  pageSize = 4;
  collectionSize: number;
  claims: IClaim[];
  allClaims: IClaim[];

  ngOnInit(): void {
    this.service.fetchClaims().subscribe(
      (response: any) => {
        console.log(response);
        this.collectionSize = response.data.length;
        this.claims = response.data;
        this.allClaims = this.claims;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
        // alert("Internal Server Error");
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal("Internal Server Error","OK");
      }
    );
  }

  search(value: string): void {
    this.claims = this.allClaims.filter(
      (val) =>
        val.claimId.toString().includes(value) ||
        val.userId.toString().includes(value) ||
        val.orderId.toString().includes(value) 
    );
    this.collectionSize = this.claims.length;
  }
  public policyDetail = [];
  fetchCustomerPolicyDetail(claim: any) 
  {
    this.service.getCustomerPolicyDetail(claim.orderId).subscribe(
      (response: any) => 
      {
        console.log(response);
        this.policyDetail = response.data;
      },
      (error: HttpErrorResponse) => {
        console.log(error.message);
        // alert("Internal Server Error");
        this.openModalObj.openCustomModal("Internal Server Error","OK");
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
        this.openModalObj.openCustomModal("Internal Server Error","OK");
      }
    );
  }

//   updateClaim(claim:any){
//     this.service.updateClaimStatus(claim.claimId).subscribe(
//       (response: any) => 
//       {
//         console.log(response);
//         this.ngOnInit();
//       },
//       (error: HttpErrorResponse) => 
//       {
//         console.log(error.message);
//         alert("Internal Server Error");
//       }
//     );
   
//   }
// }
claimId: number;
  updateClaimId(claim: any) {
    this.claimId = claim.claimId;
  }

  updateClaim() {
    this.service.updateClaimStatus(this.claimId).subscribe(
      (response) => {
        console.log(response);
        this.ngOnInit();
      },
      (err) => {
        console.log(err);
      }
    );
  }

  indianRupeeFormat(val: number) {
    return Number(val).toLocaleString('en-IN');
  }
}

