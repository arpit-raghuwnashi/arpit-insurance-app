import { Component, OnInit } from "@angular/core";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { IUnderwriter, UnderwriterRegister } from "./underwriters";
import { UnderwritersService } from "./underwriters.service";
import { ViewChild } from "@angular/core";
import { HttpErrorResponse } from "@angular/common/http";
import { AppComponent } from "app/app.component";

@Component({
  selector: "underwriters-cmp",
  moduleId: module.id,
  templateUrl: "./underwriters.component.html",
})
export class UnderwritersComponent implements OnInit {
  page = 1;
  pageSize = 4;
  collectionSize: number;
  underwriters: IUnderwriter[];
  allUnderwriters: IUnderwriter[];
  genders = ["Male", "Female", "Other"];
  response = "";
  register = new UnderwriterRegister(
    2,
    true,
    "",
    "",
    "",
    "",
    "",
    0,
    "",
    "",
    "",
    0
  );

  // public underwriters = [] as any;
  constructor(
    private service: UnderwritersService,
    private modalService: NgbModal,
    private openModalObj: AppComponent
  ) {}

  @ViewChild("details") form: any;

  ngOnInit(): void {
    this.service.getUnderwriters().subscribe(
      (respond: any) => {
        // console.log(respond);
        this.collectionSize = respond.data.length;
        this.underwriters = respond.data;
        this.allUnderwriters = this.underwriters;
      },
      (error: HttpErrorResponse) => {
        // console.log(error.message);
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal("Internal Server Error","OK");
      }
    );
  }
  search(value: string): void {
    this.underwriters = this.allUnderwriters.filter(
      (val) =>
        val.email.toLowerCase().includes(value) ||
        val.firstName.toLowerCase().includes(value) ||
        val.lastName.toLowerCase().includes(value) ||
        val.userId.toString().includes(value) ||
        val.roleId.toString().includes(value) ||
        val.education.toLowerCase().includes(value) ||
        val.gender.toLowerCase().includes(value)
    );
    this.collectionSize = this.underwriters.length;
  }
  public registerunderwriter() {
    this.service.create(this.register).subscribe(
      (data) => {
        if (data.code == 200) {
          this.response = data.message;
          this.openModalObj.openCustomModal(this.response,"OK");
          this.ngOnInit();
        } else {
          this.response = data.message;
          this.openModalObj.openCustomModal(this.response,"OK");
        }
      },
      (err) => {
        this.openModalObj.openCustomModal(err,"OK");
      }
    );
    this.form.reset();
  }
  reset() {
    this.form.reset();
  }
  deleteId: number;
  deleteUnderwriterId(underwriter: any) {
    this.deleteId = underwriter.userId;
  }

  deleteUnderwriter() {
    this.service.deleteunderwriter(this.deleteId).subscribe(
      (response) => {
        // console.log(response);
        this.ngOnInit();
      },
      (err) => {
        // console.log(err);
        this.openModalObj.openCustomModal("Down Server! Try Later ","OK");
      }
    );
  }
}
