// recoge otros modulos que se necesiten aplicar en el aplicativo, tambien recoge componentes, tuberias, guardas.


//librerias
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';  

//routers
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './template/cards/app.component';

// components
import { NewGameComponent } from './modules/game/pages/new-game/new-game.component';


@NgModule({
  //aqui van los otros componentes menos el principal
  declarations: [
    AppComponent,
    NewGameComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
