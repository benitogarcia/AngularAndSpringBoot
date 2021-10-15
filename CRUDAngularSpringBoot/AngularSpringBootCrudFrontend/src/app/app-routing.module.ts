import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DetalleProductoComponent } from './views/producto/detalle-producto.component';
import { FormProductoComponent } from './views/producto/form-producto.component';
import { ListaProductosComponent } from './views/producto/lista-productos.component';

const routes: Routes = [
  { path: '', component: ListaProductosComponent },
  { path: 'detalle/:id', component: DetalleProductoComponent },
  { path: 'nuevo', component: FormProductoComponent },
  { path: 'editar/:id', component: FormProductoComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
