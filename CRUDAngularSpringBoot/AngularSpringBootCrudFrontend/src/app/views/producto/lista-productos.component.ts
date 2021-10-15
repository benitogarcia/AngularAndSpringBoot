import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/models/producto';
import { ProductoService } from 'src/app/services/producto.service';

@Component({
  selector: 'app-lista-productos',
  templateUrl: './lista-productos.component.html',
  styleUrls: ['./lista-productos.component.css']
})
export class ListaProductosComponent implements OnInit {

  productos: Producto[]=[];

  constructor(private serviceProducto: ProductoService) { }

  ngOnInit(): void {
    this.cargarProductos();
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
