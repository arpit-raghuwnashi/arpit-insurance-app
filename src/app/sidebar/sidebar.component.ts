import { Component, OnInit } from '@angular/core';

export interface RouteInfo {
    path: string;
    title: string;
    icon: string;
    class: string;
}

export const ADMINROUTES: RouteInfo[] = [
    { path: 'admin/dashboard',     title: 'Dashboard',         icon:'nc-bank',       class: '' },
    { path: 'admin/user',          title: 'Profile',           icon:'nc-badge',      class: '' },
    { path: 'admin/products',      title: 'Products',          icon:'nc-bag-16',     class: '' },
    { path: 'admin/customers',     title: 'Customers',         icon:'nc-single-02',  class: ''  },
    { path: 'admin/underwriters',  title: 'Underwriters',      icon:'nc-hat-3',      class: ''  },
    { path: 'admin/claims',        title: 'Claims',            icon:'nc-paper',      class: ''  }
];

export const UNDERWRITERROUTES: RouteInfo[] = [
    { path: 'underwriter/dashboard',       title: 'Dashboard',         icon: 'nc-bank', class: '' },
    { path: 'underwriter/policy',          title: 'View Products',      icon: 'nc-bag-16', class: '' },
    { path: 'underwriter/users',            title: 'Users Details',    icon: 'nc-bullet-list-67', class: '' },
    { path: 'underwriter/pendingList',     title: 'Pending Request',   icon: 'nc-user-run', class: '' },
    { path: 'underwriter/declineList',     title: 'Declined Request',   icon: 'nc-paper', class: '' },
    { path: 'underwriter/underwriter',     title: 'Profile',           icon: 'nc-single-02', class: '' },

];

@Component({
    moduleId: module.id,
    selector: 'sidebar-cmp',
    templateUrl: 'sidebar.component.html',
})

export class SidebarComponent implements OnInit {
    public menuItems: any[];
    ngOnInit() {
       if(location.href.indexOf('admin')>-1){
        this.menuItems = ADMINROUTES.filter(menuItem => menuItem);
       }else{
        this.menuItems = UNDERWRITERROUTES.filter(menuItem => menuItem);
       } 
    }
}
