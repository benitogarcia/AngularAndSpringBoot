import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginUsuario } from 'src/app/models/login-usuario';
import { AuthService } from 'src/app/services/auth.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLogged = false;
  isLoginFail = false;
  loginUsuario: LoginUsuario;
  username: string = "";
  userpass: string = "";
  roles: string[] = [];
  errorMsg: string;

  constructor(private tokenService: TokenService, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {

    // validar si ya estamos logeados
    console.log("token:" + this.tokenService.getToken());

    if (this.tokenService.getToken()) {
      this.isLogged = true;
      this.isLoginFail = false;
      this.roles = this.tokenService.getAuthorities() || [];
    }
  }

  onLogin(): void {

    // Asigmanos los valores a la clase
    this.loginUsuario = new LoginUsuario(this.username, this.userpass);

    // validamos la sesion
    this.authService.login(this.loginUsuario).subscribe(
      data => {

        this.isLogged = true;
        this.isLoginFail = false;

        this.tokenService.setToken(data.token);
        this.tokenService.setUsername(data.username);
        this.tokenService.setAuthorities(data.authorities);
        this.roles = data.authorities;

        this.router.navigate(['/']);

      },
      err => {
        this.isLogged = false;
        this.isLoginFail = true;
        console.log(err);
        this.errorMsg = err.error.message;
      }
    );
  }

}
