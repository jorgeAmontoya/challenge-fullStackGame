import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modules/shared/services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(  private auth$: AuthService,
    private router: Router,
    ) { }

  ngOnInit(): void {
  }


  crearJuego(){
    this.router.navigate(['new']);//redireccionamiento a el otro componente

  }

  listgames(){
    this.router.navigate(['listaJugadores']);
  };

}
