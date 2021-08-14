import { Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from './product';
import { environment } from 'environment/environment.module';

@Injectable({providedIn: 'root'})
export class ProductService
{

    private apiServerUrl = environment.projecturl;

    constructor(private http: HttpClient){}

    public getProducts(): Observable<Product[]>
    {
        return this.http.get<Product[]>(`${this.apiServerUrl}/products`);
    }

    public addProduct(product : Product): Observable<Product>
    {
        // console.log(product); 
        return this.http.post<Product>(`${this.apiServerUrl}/products`, product);
    } 

    public updateProduct(product : Product): Observable<Product>
    {
        // console.log(product);
        return this.http.put<Product>(`${this.apiServerUrl}/updateproduct`, product);
    }

    public deleteProduct(product_id : number): Observable<void>
    {
        return this.http.delete<void>(`${this.apiServerUrl}/deleteproduct/${product_id}`);
    }
}

