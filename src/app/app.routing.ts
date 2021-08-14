import { Routes } from '@angular/router';
import { AboutUsComponent } from './about-us/about-us.component';
import { AuthGuard } from './authservice/auth.guard';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { EmpLoginComponent } from './emp-login/emp-login.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { LandingpageComponent } from './landingpage/landingpage.component';

import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { LoginComponent } from './login/login.component';
import { OrderPolicesComponent } from './order-polices/order-polices.component';
import { PaymentComponent } from './payment/payment.component';
import { PoliciesComponent } from './policies/policies.component';
import { RegistrationComponent } from './registration/registration.component';
import { UnderwriterLayoutComponent } from './underwriter/layouts/admin-layout/admin-layout.component';
import { UserprofileComponent } from './userprofile/userprofile.component';

export const AppRoutes: Routes = [
  { path: '', component: LandingpageComponent },
  { path: 'login-component', component: LoginComponent},
  { path: 'register-component', component: RegistrationComponent },
  { path: 'order-polices/:id', component: OrderPolicesComponent ,canActivate: [AuthGuard]},
  { path: 'payment/:id', component: PaymentComponent,canActivate: [AuthGuard] },
  { path: 'payment', component: PaymentComponent,canActivate: [AuthGuard] },
  { path: 'policy-component/:id', component: PoliciesComponent },
  { path: 'userprofile-component', component: UserprofileComponent, canActivate: [AuthGuard]},
  { path: 'emp-login', component: EmpLoginComponent },
  { path: 'forgetPass', component: ForgetPasswordComponent },
  { path: 'about-us', component: AboutUsComponent },
  { path: 'contact-us', component: ContactUsComponent },
  { path: 'admin', component: PaymentComponent,canActivate: [AuthGuard] },
  { path: 'underwriter', component: PaymentComponent,canActivate: [AuthGuard] },
  { path: 'access-denied', component: ForbiddenComponent },
  {
    path: '',
    component: AdminLayoutComponent,
    children: [
      {
        path: 'admin',
        loadChildren: () => import('./layouts/admin-layout/admin-layout.module').then(m => m.AdminLayoutModule)
        // loadChildren: './layouts/admin-layout/admin-layout.module#AdminLayoutModule'
      }
    ]
  },
  {
    path: '',
    component: UnderwriterLayoutComponent,
    children: [
      {
        path: 'underwriter',
        loadChildren: () => import('./underwriter/layouts/admin-layout/admin-layout.module').then(m => m.UnderwriterLayoutModule)
        // loadChildren: './layouts/admin-layout/admin-layout.module#AdminLayoutModule'
      }
    ]
  },
]
