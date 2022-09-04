import { Injectable, NgZone } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router';
import { userGoogle } from '../models/user-google.model';
import {GoogleAuthProvider } from 'firebase/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user :any ;

  constructor(
    private router: Router,
    private afAuth: AngularFireAuth,
    private ngZone: NgZone
    ) {
    this.afAuth.authState.subscribe(usuario =>{
      this.user = usuario;
    });
   }

   logout(): void{
    this.afAuth.signOut().then((_res) =>{
      this.ngZone.run(() => {
        this.router.navigate(['']);
    })
  });
}

  OAuthProvider(provider: any){
    return this.afAuth.signInWithPopup(provider)
      .then((_res) => {
       _res.user?.getIdToken().then((token)=>{
          console.log(token);
          
       });
        this.ngZone.run(() => {
          this.router.navigate(['game/new']);//redireccionamiento a el otro componente
        })
      }).catch((error) => {
        window.alert(error)
      })
  }

   SigninWithGoogle(): Promise<void>{
     return this.OAuthProvider(new GoogleAuthProvider())
     .then(res =>{
       console.log('successfully logged in!')
     }).catch(error  =>{
       console.log(error)
     
     });
       
   }
}
