import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ToastrModule } from "ngx-toastr";
import { CarouselModule } from 'ngx-owl-carousel-o'

import { SidebarModule } from './sidebar/sidebar.module';
import { FooterModule } from './shared/footer/footer.module';
import { NavbarModule} from './shared/navbar/navbar.module';
import { FixedPluginModule} from './shared/fixedplugin/fixedplugin.module';

import { AppComponent } from './app.component';
import { AppRoutes } from './app.routing';

import { AdminLayoutComponent } from './layouts/admin-layout/admin-layout.component';
import { UnderwriterLayoutComponent } from './underwriter/layouts/admin-layout/admin-layout.component';

import { BrowserModule } from '@angular/platform-browser';
import { ProductService } from './pages/products/product.service';  
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ClaimsComponent } from './pages/claims/claims.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProductsComponent } from "./pages/products/products.component";
import { ReactiveFormsModule } from '@angular/forms';
import { LandingpageComponent } from "./landingpage/landingpage.component";
import { RegistrationComponent } from './registration/registration.component'
import { PoliciesComponent } from "./policies/policies.component";
import { OrderPolicesComponent } from "./order-polices/order-polices.component";
import { PaymentComponent } from "./payment/payment.component";
import { UserprofileComponent } from "./userprofile/userprofile.component";
import { HeaderComponent } from "./header/header.component";
import { FooterComponent } from "./footer/footer.component";
import { MainSliderComponent } from "./main-slider/main-slider.component";
import { LoginComponent } from "./login/login.component";
import { EmpLoginComponent } from './emp-login/emp-login.component';
import { AuthInterceptor } from "./authservice/auth.interceptor";
import { PolicyComponent } from './underwriter/pages/policy/policy.component';
import { PendingListComponent } from './underwriter/pages/pending-list/pending-list.component';
import { DeclineListComponent } from './underwriter/pages/decline-list/decline-list.component';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { UsersComponent } from './underwriter/pages/users/users.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { MustMatchDirective } from './registration/must-match.directive';
import { AboutUsComponent } from './about-us/about-us.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { NgxPrintModule } from 'ngx-print';
import { ForbiddenComponent } from './forbidden/forbidden.component';


@NgModule({
  declarations: [
    AppComponent,
    AdminLayoutComponent,
    UnderwriterLayoutComponent,
    ClaimsComponent,
    ProductsComponent,
    LandingpageComponent,
    LoginComponent,
    RegistrationComponent,
    PoliciesComponent,
    OrderPolicesComponent,
    PaymentComponent,
    UserprofileComponent,
    HeaderComponent,
    FooterComponent,
    MainSliderComponent,
    EmpLoginComponent,
    PolicyComponent,
    PendingListComponent,
    DeclineListComponent,
    ForgetPasswordComponent,
    UsersComponent,
    MustMatchDirective,
    AboutUsComponent,
    ContactUsComponent,
    ForbiddenComponent,

  ],
  imports: [
    BrowserAnimationsModule,
    RouterModule.forRoot(AppRoutes,{
        
    }),
    SidebarModule,
    NavbarModule,
    ToastrModule.forRoot(),
    FooterModule,
    FixedPluginModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    NgbModule,
    ReactiveFormsModule,
    CarouselModule,
    NgxPrintModule,
    MatSnackBarModule
  
  ],
  providers: [ProductService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
