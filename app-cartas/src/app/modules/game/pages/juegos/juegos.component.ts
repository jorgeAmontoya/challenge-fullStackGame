import { Component, OnInit } from '@angular/core';
import { JuegoModel22 } from '../../models/juego.model'; 
import { Router } from '@angular/router';
import { JuegoServiceService } from '../../services/juego-service.service';
import { AuthService } from 'src/app/modules/shared/services/auth.service';
import firebase from 'firebase/compat'
import { WebSocketserviceTsService } from '../../services/web-socketservice.ts.service';


@Component({
  selector: 'app-juegos',
  templateUrl: './juegos.component.html',
  styleUrls: ['./juegos.component.scss']
})
export class JuegosComponent implements OnInit {
  
  dataSource: JuegoModel22[] = [];
  currentUser!: firebase.User | null

  constructor(private router: Router, private juegoservice: JuegoServiceService, private auth$:AuthService, private Websocket$: WebSocketserviceTsService) { }

  async ngOnInit(){
  
    this.currentUser = await this.auth$.getUserAuth();
    this.juegoservice.listarJuegos(this.currentUser!.uid).subscribe(juego => this.dataSource=juego)

  }
entrar(Id:string){
  this.router.navigate([`tablero/${Id}`]);
}
//this.router.navigate(['tablero']);
iniciar(id: string){
this.Websocket$.conection(id).subscribe({

  next: (event:any) => {
    if(event.type === 'cardgame.tablerocreado'){     
      this.juegoservice.crearRonda({
          juegoId: id,
          tiempo: 60,
          jugadores: event.jugadorIds.map((it:any) => it.uuid) 
        });
      }
     
      if(event.type == 'cardgame.rondacreada'){
        this.router.navigate(['tablero/'+id]);
      }
      
    },
    error: (err:any) => console.log(err),
    complete: () => {console.log('complete')}
  });

  this.juegoservice.iniciar({ juegoId: id }).subscribe();
}
 
 

}
