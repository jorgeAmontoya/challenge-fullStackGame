import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NewGameComponent } from './game/pages/new-game/new-game.component';
import { HomeComponent } from './game/pages/home/home.component';
import { JuegosComponent } from './game/pages/juegos/juegos.component';
import { TableroComponent } from './game/pages/tablero/tablero.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { JuegoRoutingsModule } from './juego-routing.module'; 
import { NavbarComponent } from './game/components/navbar/navbar.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    JuegoRoutingsModule
  ],
  declarations: [
  NewGameComponent,
  HomeComponent,
  JuegosComponent,
  TableroComponent,
  NavbarComponent,]
})
export class GameModuleModule { }