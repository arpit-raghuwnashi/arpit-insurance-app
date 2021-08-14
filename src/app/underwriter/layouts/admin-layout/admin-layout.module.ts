import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { DashboardComponent } from '../../pages/dashboard/dashboard.component';
import { UnderwriterComponent } from '../../pages/underwriter/underwriter.component';
import { UnderwriterLayoutRoutes } from './admin-layout.routing';

@NgModule({
  imports: [
    RouterModule.forChild(UnderwriterLayoutRoutes),
    FormsModule,
    CommonModule
  ],
  declarations: [
    DashboardComponent,
    UnderwriterComponent,
  ]
})

export class UnderwriterLayoutModule {}
