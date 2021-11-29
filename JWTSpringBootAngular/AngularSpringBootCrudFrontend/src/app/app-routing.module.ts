import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProdGuardService } from './services/guards/prod-guard.service';
import { LoginComponent } from './views/auth/login.component';
import { RegistrarComponent } from './views/auth/registrar.component';
import { IndexComponent } from './views/index/index.component';
import { DetalleProductoComponent } from './views/producto/detalle-producto.component';
import { FormProductoComponent } from './views/producto/form-producto.component';
import { ListaProductosComponent } from './views/producto/lista-productos.component';

const routes: Routes = [
  { path: '', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registrar', component: RegistrarComponent },
  { path: 'lista', component: ListaProductosComponent, canActivate: [ProdGuardService], data: { expectedRol: ['admin', 'user'] } },
  { path: 'detalle/:id', component: DetalleProductoComponent, canActivate: [ProdGuardService], data: { expectedRol: ['admin', 'user'] } },
  { path: 'nuevo', component: FormProductoComponent, canActivate: [ProdGuardService], data: { expectedRol: ['admin'] } },
  { path: 'editar/:id', component: FormProductoComponent, canActivate: [ProdGuardService], data: { expectedRol: ['admin'] } },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
