import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Para cliente con el servidor
import { HttpClientModule } from '@angular/common/http';
// Para los formulario
import { FormsModule } from '@angular/forms';

// External
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
// Views
import { ListaProductosComponent } from './views/producto/lista-productos.component';
import { DetalleProductoComponent } from './views/producto/detalle-producto.component';
import { FormProductoComponent } from './views/producto/form-producto.component';

@NgModule({
  declarations: [
    AppComponent,
    ListaProductosComponent,
    DetalleProductoComponent,
    FormProductoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
