import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CustomersComponent } from 'app/pages/customers/customers.component';
import { UnderwritersComponent } from 'app/pages/underwriters/underwriters.component';
import { DashboardComponent } from '../../pages/dashboard/dashboard.component';
import { UserComponent } from '../../pages/user/user.component';
import { AdminLayoutRoutes } from './admin-layout.routing';

@NgModule({ 
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    NgbModule,
    ReactiveFormsModule,
  ],
  declarations: [
    DashboardComponent,
    UserComponent,
    CustomersComponent,
    UnderwritersComponent,
    
  ]
})

export class AdminLayoutModule {}
