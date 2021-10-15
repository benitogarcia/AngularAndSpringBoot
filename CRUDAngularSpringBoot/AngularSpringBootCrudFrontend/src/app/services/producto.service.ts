import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../models/constants';
import { Producto } from '../models/producto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  baseUrlApi: string = "";
  constructor(private http: HttpClient) {
    this.baseUrlApi = Constants.BASE_API_V1;
  }

  public save(prod: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.baseUrlApi + "productos/save", prod);
  }

  public lista(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.baseUrlApi + "productos/all");
  }

  public searchById(id: number): Observable<Producto> {
    return this.http.get<Producto>(this.baseUrlApi + `productos/search/id/${id}`);
  }
}
