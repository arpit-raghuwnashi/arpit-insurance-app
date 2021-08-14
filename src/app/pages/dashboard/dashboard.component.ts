import { HttpErrorResponse } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import Chart from "chart.js";
import { ICount } from "./dashboard";
import { DashboardService } from "./dashboard.service";
import { AppComponent } from "app/app.component";

@Component({
  selector: "dashboard-cmp",
  moduleId: module.id,
  templateUrl: "dashboard.component.html",
})
export class DashboardComponent implements OnInit {
  constructor(private dashboardService: DashboardService, private openModalObj: AppComponent) {}

  public count: ICount;

  ngOnInit() 
  {
    this.dashboardService.count().subscribe((respond:any) => 
    {
      console.log(respond);
      this.count = respond.data;
      // console.log(this.count.totalPolicies);
    },
    (error: HttpErrorResponse) => {
      console.log(error.message);
      // alert("Internal Server Error");
      if(error.error.status==403){
        location.href='/access-denied';
      }
      this.openModalObj.openCustomModal("Bad Request", "OK");
    });
  }
}
