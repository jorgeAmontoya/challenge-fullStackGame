// recoge otros modulos que se necesiten aplicar en el aplicativo, tambien recoge componentes, tuberias, guardas.


//librerias
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';  
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { AngularFireModule} from '@angular/fire/compat';
import {AngularFireAuthModule} from '@angular/fire/compat/auth';


// Material
//import { MatButtonModule } from '@angular/mateiral/button';

//routers
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './template/cards/app.component';

//Environments
import { environment } from 'src/environments/environment';

// components
import { NewGameComponent } from './modules/game/pages/new-game/new-game.component';
import { LogInComponent } from './modules/game/pages/log-in/log-in.component';
import { HomeComponent } from './modules/game/pages/home/home.component';
import { JuegosComponent } from './modules/game/pages/juegos/juegos.component';
import { TableroComponent } from './modules/game/pages/tablero/tablero.component';
import { SpinnerComponent } from './modules/game/components/spinner/spinner.component';
import { NavbarComponent } from './modules/game/components/navbar/navbar.component';


@NgModule({
  //aqui van los otros componentes menos el principal
  declarations: [
    AppComponent,
    NewGameComponent,
    LogInComponent,
    HomeComponent,
    JuegosComponent,
    TableroComponent,
    SpinnerComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    //MatButtonModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireAuthModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
