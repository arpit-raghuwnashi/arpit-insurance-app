import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { EmpLoginService } from "app/emp-login/emp-login.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private loginService: EmpLoginService, private router: Router) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    if (!this.loginService.isLoggedIn()) {
      var currentPath = window.location.pathname;
      var empPath = window.location.href;
      if (currentPath == "/admin" || currentPath == '/underwriter') {
        this.router.navigate(['/emp-login']);
        return false;
      }
      if (empPath.indexOf('/admin/') > -1 || empPath.indexOf('/underwriter/')>-1) {
        this.router.navigate(['/emp-login']);
        return false;
      }
      this.router.navigate(['/login-component']);
      return false;
    }
    return true;
  }
}
