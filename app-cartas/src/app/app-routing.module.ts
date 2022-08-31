// sistema de rutas
// librerias
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

//components
import { NewGameComponent } from './modules/game/pages/new-game/new-game.component';


// se crean las rutas
const routes: Routes = [
  {
    path:'game/new',
    component: NewGameComponent

  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
