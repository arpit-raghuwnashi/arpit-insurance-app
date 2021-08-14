import { Routes } from '@angular/router';
import { AuthGuard } from 'app/authservice/auth.guard';
import { DeclineListComponent } from 'app/underwriter/pages/decline-list/decline-list.component';
import { PendingListComponent } from 'app/underwriter/pages/pending-list/pending-list.component';
import { PolicyComponent } from 'app/underwriter/pages/policy/policy.component';
import { UnderwriterComponent } from 'app/underwriter/pages/underwriter/underwriter.component';
import { UsersComponent } from 'app/underwriter/pages/users/users.component';
import { DashboardComponent } from '../../pages/dashboard/dashboard.component';


export const UnderwriterLayoutRoutes: Routes = [
    { path: 'dashboard',      component: DashboardComponent , canActivate: [AuthGuard]},
    { path: 'users',           component: UsersComponent , canActivate: [AuthGuard]},
    { path: 'underwriter',    component: UnderwriterComponent , canActivate: [AuthGuard] },
    { path: 'policy',         component: PolicyComponent, canActivate: [AuthGuard] }, 
    { path: 'pendingList',    component: PendingListComponent, canActivate: [AuthGuard] },
    { path: 'declineList',    component: DeclineListComponent, canActivate: [AuthGuard] },

];
