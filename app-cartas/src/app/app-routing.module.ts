// sistema de rutas
// librerias
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EjemploGuard } from './modules/game/guards/ejemplo.guard';
import { LogInComponent } from './modules/game/pages/log-in/log-in.component';
import { AngularFireAuthGuard, redirectLoggedInTo, redirectUnauthorizedTo, } from '@angular/fire/compat/auth-guard';

const redirectUnauthorizedToLogin = () => redirectUnauthorizedTo(['']);
const redirectLoggedInToDashboard = () => redirectLoggedInTo(['game/new']);
const redirectLoggedInToDashboardHome = () => redirectLoggedInTo(['crear']);

//components
import { NewGameComponent } from './modules/game/pages/new-game/new-game.component';
import { HomeComponent } from './modules/game/pages/home/home.component';

// se crean las rutas
const routes: Routes = [
  {
    
    path:'',
    component: LogInComponent,
    canActivate: [AngularFireAuthGuard],
    data: {authGuardPipe: redirectLoggedInToDashboardHome},
  },
  {
    path: 'crear',
    component: HomeComponent,
    canActivate: [AngularFireAuthGuard],
    data: {authGuardPipe:  redirectUnauthorizedToLogin}
  },

  {
    path:'game/new',
    component:NewGameComponent,
    canActivate: [AngularFireAuthGuard],
    data: {authGuardPipe: redirectUnauthorizedToLogin},

  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
