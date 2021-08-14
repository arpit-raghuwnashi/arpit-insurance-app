import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'app/app.component';
import { Product } from '../model/product';
import { UnderwriterService } from '../services/underwriter.service';

@Component({
  selector: 'app-policy',
  templateUrl: './policy.component.html',
  styleUrls: ['./policy.component.css']
})
export class PolicyComponent implements OnInit {

  productsDetails : Product[];

  constructor(private underwriterService:UnderwriterService, private openModalObj: AppComponent) { }

  ngOnInit(): void {
    this.getAllProductsDetails();
  }

   // retrievinf all product details;
   getAllProductsDetails():void{
    this.underwriterService.getProductDetails().subscribe(
      (response:any) => {
        this.productsDetails=response;
      },
      (error:HttpErrorResponse)=>{
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal("OOPS! Something went wrong", "OK");
      }
    );
  }

  indianRupeeFormat(val: number) {
    return Number(val).toLocaleString('en-IN');
  }

}
