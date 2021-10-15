import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, UrlTree } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Producto } from 'src/app/models/producto';
import { ProductoService } from 'src/app/services/producto.service';

@Component({
  selector: 'app-form-producto',
  templateUrl: './form-producto.component.html',
  styleUrls: ['./form-producto.component.css']
})
export class FormProductoComponent implements OnInit {

  id: number = 0;
  nombre: string = "";
  precio: number = 0;
  urlTree: UrlTree = new UrlTree;
  producto!: Producto;

  constructor(
    private router: Router, 
    private activateRouter: ActivatedRoute, 
    private serviceProducto: ProductoService, 
    private toast: ToastrService) {

  }

  ngOnInit(): void {

    this.id = this.activateRouter.snapshot.params.id;

    this.id = (this.id == undefined) ? 0 : this.id;

    console.log("buscar producto por0:" + this.id);
    if (this.id > 0) {
      console.log("buscar producto por1:" + this.id);
      this.buscarProductoEditar(this.id);
    }
  }

  buscarProductoEditar(id: number): void {
    console.log("buscar producto por2:" + id);
    this.serviceProducto.searchById(id).subscribe(
      data => {
        this.producto = data;
        this.nombre = data.nombre;
        this.precio = data.precio;
      },
      err => {
        console.log(err);
      }
    );
  }

  save(): void {
    this.producto = new Producto(this.id, this.nombre, this.precio);

    const msgSuccess = ((this.id > 0) ? "edito" : "creo");

    this.serviceProducto.save(this.producto).subscribe(
      data => {

        this.toast.success("Se " + msgSuccess + " el producto", 'OK!', {
          timeOut: 3000
        });
        this.id = data.id;
        this.router.navigate(["/"]);
      },
      err => {
        this.toast.error('Error ' + msgSuccess + ' el producto.<br>' + err.error.message, 'ERROR!', {
          timeOut: 3000,
          enableHtml: true
        });
      }
    );


  }



}
