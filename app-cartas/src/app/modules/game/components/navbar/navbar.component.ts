import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/modules/shared/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  constructor(private router: Router, private authService: AuthService) { }
  isLogged: boolean = this.authService.isLoggedIn;
  user: any = JSON.parse(localStorage.getItem('user')!);
  puntaje: number = +JSON.parse(localStorage.getItem('puntaje')!);

  ngOnInit(): void {
  }
  inicio(){
    this.router.navigate(['crear'])
  }
  list(){
    this.router.navigate(['list'])
  }
  nuevoJuego(){
    this.router.navigate(['game/new'])
  }
  logout(){
    this.authService.logout()
    .then(() => {
      //this.isLogged = this.authService.isLoggedIn
      this.router.navigate(['']);

    })
    .catch((error) => {
      window.alert(error)
    })
  }

}
