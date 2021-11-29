import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from '../models/constants';
import { JwtDto } from '../models/jwt-dto';
import { LoginUsuario } from '../models/login-usuario';
import { NuevoUsuario } from '../models/nuevo-usuario';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrlApi: string = "";
  constructor(private http: HttpClient) {
    this.baseUrlApi = Constants.BASE_API_V1_AUTH;
  }

  public nuevo(nuevo: NuevoUsuario): Observable<any> {
    return this.http.post<any>(this.baseUrlApi + 'register', nuevo);
  }

  public login(login: LoginUsuario): Observable<JwtDto> {
    return this.http.post<JwtDto>(this.baseUrlApi + 'login', login);
  }


}
