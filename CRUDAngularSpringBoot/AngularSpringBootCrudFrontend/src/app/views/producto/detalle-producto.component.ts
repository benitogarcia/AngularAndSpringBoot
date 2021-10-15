import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProductoService } from 'src/app/services/producto.service';

@Component({
  selector: 'app-detalle-producto',
  templateUrl: './detalle-producto.component.html',
  styleUrls: ['./detalle-producto.component.css']
})
export class DetalleProductoComponent implements OnInit {

  id = 0;
  nombre = "";
  precio = 0;

  constructor(
    private productoService: ProductoService,
    private activatedRoute: ActivatedRoute,
    private toast: ToastrService) { }

  ngOnInit(): void {

    this.id = this.activatedRoute.snapshot.params.id || 0;
    this.cargarProducto(this.id);
  }

  cargarProducto(id: number): void {
    this.productoService.searchById(id).subscribe(
      data => {
        this.nombre = data.nombre;
        this.precio = data.precio;
      },
      err => {
        const msg = err.error.message || "";
        this.toast.error('Error al cargar el producto.<br>'+msg, 'Error!', {
          timeOut: 3000,
          enableHtml: true
        });
      }
    );
  }

}
