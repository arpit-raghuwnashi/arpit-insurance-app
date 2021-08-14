import { Component, OnInit } from '@angular/core';
import { Product } from './product';
import { ProductService } from './product.service';
import { HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AppComponent } from "app/app.component";

@Component({
  selector: 'products-cmp',
  moduleId: module.id,
  templateUrl: 'products.component.html'
})
export class ProductsComponent implements OnInit {
  public products: Product[];
  closeResult: string;
  editForm: FormGroup;
  private deleteId: number;
  collectionSize: number;
  allProducts: Product[];

  constructor(
    private productService: ProductService,
    private modalService: NgbModal,
    private myformbuilder: FormBuilder,
    private openModalObj: AppComponent) {

  }

  ngOnInit(): void {
    this.getProducts(); // Get all products from here by calling getproducts method below
    this.editForm = this.myformbuilder.group({
      productId: [''],
      policyId: [''],
      productName: ['', Validators.required],
      sumAssured: ['', Validators.required],
      createdDate: ['', Validators.required],
      modifiedDate: ['', Validators.required],
      numberOfYearsCovered: ['', Validators.required],
      minAgeLimit: ['', Validators.required],
      maxAgeLimit: ['', Validators.required],
      minNumDependents: ['', Validators.required],
      maxNumDependents: ['', Validators.required],
      productDescription: ['', Validators.required]
    });
  }

  public getProducts(): void {
    this.productService.getProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
        this.collectionSize = response.length;
        this.allProducts = this.products;
        // console.log(this.products);
        
      },
      (error: HttpErrorResponse) => {
        // console.error(error)
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal("OOPS! No Products Available", "OK");
      }
    );
  }

  search(value: string): void {
    this.products = this.allProducts.filter(
      (val) =>
        val.productName.toLowerCase().includes(value) ||
        val.productDescription.toLowerCase().includes(value) ||
        val.sumAssured.toString().includes(value) ||
        val.policyId.toString().includes(value) ||
        val.numberOfYearsCovered.toString().includes(value) ||
        val.minAgeLimit.toString().includes(value) ||
        val.maxAgeLimit.toString().includes(value) ||
        val.createdDate.toString().includes(value) ||
        val.modifiedDate.toString().includes(value)
    );
    this.collectionSize = this.products.length;
  }

  public open(content) {
    this.modalService.open(content, { ariaLabelledBy: 'addProductModal' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
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

  public onSubmit(addform: NgForm) {
    this.productService.addProduct(addform.value).subscribe(
      (result: Product) => {
        // console.log(result);
        this.ngOnInit(); //reload the products
      },
      (error: HttpErrorResponse) => {
        // alert(error.message);
        this.openModalObj.openCustomModal(error.message, "OK");
      });
    this.modalService.dismissAll(); //dismiss modal
  }

  openDetails(targetModal, product: Product) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
    document.getElementById('PID').setAttribute('value', String(product.productId));
    document.getElementById('PRONAME').setAttribute('value', String(product.productName));
    document.getElementById('SUMASS').setAttribute('value', String(this.indianRupeeFormat(product.sumAssured)));
    document.getElementById('DATECREATE').setAttribute('value', String(product.createdDate));
    document.getElementById('MODDATE').setAttribute('value', String(product.modifiedDate));
    document.getElementById('YEARSCOV').setAttribute('value', String(product.numberOfYearsCovered));
    document.getElementById('MINAGE').setAttribute('value', String(product.minAgeLimit));
    document.getElementById('MAXAGE').setAttribute('value', String(product.maxAgeLimit));
    document.getElementById('MINDEP').setAttribute('value', String(product.minNumDependents));
    document.getElementById('MAXDEP').setAttribute('value', String(product.maxNumDependents));
    document.getElementById('DESC').setAttribute('value', String(product.productDescription));
  }

  openEdit(targetModal, product: Product) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });

    this.editForm.patchValue({
      productId: product.productId,
      policyId: product.policyId,
      productName: product.productName,
      sumAssured: product.sumAssured,
      createdDate: product.createdDate,
      modifiedDate: product.modifiedDate,
      numberOfYearsCovered: product.numberOfYearsCovered,
      minAgeLimit: product.minAgeLimit,
      maxAgeLimit: product.maxAgeLimit,
      minNumDependents: product.minNumDependents,
      maxNumDependents: product.maxNumDependents,
      productDescription: product.productDescription
    });
  }

  onSave() {
    // console.log(this.editForm.value);
    this.productService.updateProduct(this.editForm.value).subscribe(
      (result: Product) => {
        // console.log(result);
        this.ngOnInit(); //reload the products
      },
      (error: HttpErrorResponse) => {
        // alert(error.message);
        this.openModalObj.openCustomModal(error.message, "OK");
      });
    this.modalService.dismissAll(); //dismiss modal
  }

  openDelete(targetModal, product: Product) {
    this.deleteId = product.productId;
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'md'
    });
  }

  onDelete() {
    this.productService.deleteProduct(this.deleteId).subscribe(
      (result) => {
        // console.log(result);
        this.ngOnInit(); //reload the products
      },
      (error: HttpErrorResponse) => {
        // alert(error.message);

        this.openModalObj.openCustomModal(error.message, "OK");
      });
    this.modalService.dismissAll();
  }

  indianRupeeFormat(val: number) {
    return Number(val).toLocaleString('en-IN');
  }
}
