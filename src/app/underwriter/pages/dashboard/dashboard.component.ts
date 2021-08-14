import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'app/app.component';
import { IDashboardCount } from '../model/dashboradCount';
import { UnderwriterService } from '../services/underwriter.service';


@Component({
  selector: 'dashboard-cmp',
  moduleId: module.id,
  templateUrl: 'dashboard.component.html'
})

export class DashboardComponent implements OnInit {

  constructor(private underwriterService: UnderwriterService,private openModalObj: AppComponent) { }

  public dashborad: IDashboardCount;

  ngOnInit() {
    this.getDashboardCounts();
  }

  // retrieving dashbaord counts and initialise IDashboardCount vairiables
  getDashboardCounts() {
    this.underwriterService.getDashboardCounts().subscribe(
      (response:any) => {
      this.dashborad = response.data;
    },
      (error: HttpErrorResponse) => {
        if(error.error.status==403){
          location.href='/access-denied';
        }
        this.openModalObj.openCustomModal("OOPS! Something went wrong","OK");
      });
  }

}
