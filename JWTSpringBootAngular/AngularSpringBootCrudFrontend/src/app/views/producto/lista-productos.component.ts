import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/models/producto';
import { ProductoService } from 'src/app/services/producto.service';
import { TokenService } from 'src/app/services/token.service';

@Component({
  selector: 'app-lista-productos',
  templateUrl: './lista-productos.component.html',
  styleUrls: ['./lista-productos.component.css']
})
export class ListaProductosComponent implements OnInit {

  productos: Producto[] = [];
  roles: string[];
  isAdmin = false;

  constructor(private serviceProducto: ProductoService, private tokenServices: TokenService) { }

  ngOnInit(): void {
    this.cargarProductos();
    this.roles = this.tokenServices.getAuthorities();
    this.roles.forEach(rol => {
      if (rol === "ROLE_ADMIN") {
        this.isAdmin = true;
      }
    });
  }

  cargarProductos(): void {
    this.serviceProducto.lista().subscribe(
      data => {
        this.productos = data;
      },
      err => {
        console.log(err)
      }
    );
  }

  eliminar(id: number): void {
    console.log("Eliminar producto con id:" + id);
  }

}
