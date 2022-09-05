import { Injectable, NgZone } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router';
import { userGoogle } from '../models/user-google.model';
import {AuthProvider, GoogleAuthProvider, User } from 'firebase/auth';
import { JugadoresService } from '../../game/services/jugadores.service';



@Injectable({
  providedIn: 'root'
})
export class AuthService {
 // private users: Observable<Usuario[]>;

  constructor(
    private ngZone: NgZone,
    private router: Router,
    private afAuth: AngularFireAuth,
   private gamers$: JugadoresService

    ) {
      //this.users = this.usersCollection.valueChanges(['added', 'removed']);
       // .pipe(distinctUntilChanged((prev, curr) => _.isEqual(prev, curr)));
      }

   logout(): void{
    this.afAuth.signOut().then((_res) =>{
      this.ngZone.run(() => {
        this.router.navigate(['']);
    })
  });
}



async getUserAuth() {
  const userData = await this.afAuth.currentUser;
  return userData;
}


   SigninWithGoogle(): Promise<void>{
     return this.OAuthProvider(new GoogleAuthProvider())
     .then(res =>{
       console.log('successfully logged in!')
     }).catch(error  =>{
       console.log(error)
     
     });

     

   }
   private OAuthProvider(provider: AuthProvider){
    return this.afAuth.signInWithPopup(provider)
      .then((_res) => {
        this.gamers$.addGamer(_res.user);     
        this.ngZone.run(() => {
          //this.router.navigate(['game/new']);//redireccionamiento a el otro componente
          this.router.navigate(['crear']);
        })
      }).catch((error) => {
        window.alert(error)
      })
  }

 
}
