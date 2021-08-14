import { Routes } from "@angular/router";
import { AuthGuard } from "app/authservice/auth.guard";
import { ClaimsComponent } from "app/pages/claims/claims.component";
import { CustomersComponent } from "app/pages/customers/customers.component";
import { UnderwritersComponent } from "app/pages/underwriters/underwriters.component";
import { DashboardComponent } from "../../pages/dashboard/dashboard.component";
import { UserComponent } from "../../pages/user/user.component";
import { ProductsComponent } from "../../pages/products/products.component";


export const AdminLayoutRoutes: Routes = [
  { path: "dashboard", component: DashboardComponent ,canActivate: [AuthGuard]},
  { path: "user", component: UserComponent , canActivate: [AuthGuard]},
  { path: "products", component: ProductsComponent , canActivate: [AuthGuard] },
  { path: "underwriters", component: UnderwritersComponent , canActivate: [AuthGuard]},
  { path: "customers", component: CustomersComponent , canActivate: [AuthGuard] },
  { path: "claims", component: ClaimsComponent , canActivate: [AuthGuard]},
  
];
