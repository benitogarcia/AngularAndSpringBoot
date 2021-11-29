import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { NuevoUsuario } from 'src/app/models/nuevo-usuario';
import { AuthService } from 'src/app/services/auth.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-registrar',
  templateUrl: './registrar.component.html',
  styleUrls: ['./registrar.component.css']
})
export class RegistrarComponent implements OnInit {

  isRegister = false;
  isRegisterFail = false;

  registerUsuario: NuevoUsuario;

  nombre: string = "";
  username: string = "";
  userpass: string = "";
  email: string = "";

  roles: string[] = [];
  errorMsg: string;

  constructor(
    private tokenService: TokenService,
    private authService: AuthService,
    private router: Router,
    private toastService: ToastrService
  ) { }

  ngOnInit(): void {
    if (this.tokenService.getToken()) {
      this.isRegister = true;
      this.isRegisterFail = false;
      this.roles = this.tokenService.getAuthorities() || [];
    }
  }

  onRegister(): void {

    // Asigmanos los valores a la clase
    this.registerUsuario = new NuevoUsuario(this.nombre, this.username, this.userpass, this.email,);

    // validamos la sesion
    this.authService.nuevo(this.registerUsuario).subscribe(
      data => {

        this.toastService.success('Cuenta Creada', 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });

        this.router.navigate(['/login']);

      },
      err => {
        this.isRegister = false;
        this.isRegisterFail = true;
        console.log(err);
        this.errorMsg = err.error.message;
        this.toastService.error('Error al Creada', 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
      }
    );
  }
}

