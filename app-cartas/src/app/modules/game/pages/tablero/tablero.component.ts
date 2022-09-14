import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Carta } from 'src/app/modules/shared/models/mazo';
import { AuthService } from 'src/app/modules/shared/services/auth.service';
import { JuegoServiceService } from '../../services/juego-service.service';
import { WebSocketserviceTsService } from '../../services/web-socketservice.ts.service';

@Component({
  selector: 'app-tablero',
  templateUrl: './tablero.component.html',
  styleUrls: ['./tablero.component.scss']
})
export class TableroComponent implements OnInit {

  juegoId: string = "";
  uid: string = "";
  tiempo: number = 0;
  jugadoresRonda: number = 0;
  jugadoresTablero: number = 0;
  numeroRonda: number = 0;
  roundStarted:boolean = false;
  cartasDelJugador: Carta[] = [];
  cartasDelTablero: Carta[] = [];
  
  
  cartasJugadorTablero: string[] = []
  ganadorAlias:string = "";
  ganador:boolean = false;




  constructor(public juegoService$: JuegoServiceService,
    public authService: AuthService,
    public ws: WebSocketserviceTsService,
    private route: ActivatedRoute,
    private router: Router) { }


  
  ngOnInit(){
    this.route.params.subscribe((params) => {
      this.juegoId = params['id'];

      this.uid = this.authService.obtenerUsuarioSesion().uid;
      console.log(this.uid)
      this.juegoService$.getMiMazo(this.uid,this.juegoId).subscribe((element:any) => {
        this.cartasDelJugador= element.cartas;
        console.log(this.cartasDelJugador);
      });

     

     
      
      this.ws.conection(this.juegoId).subscribe({
        next: (event:any) => {

          if(event.type === 'cardgame.ponercartaentablero'){
            this.cartasDelTablero.push({
              cartaId: event.carta.cartaId,
              poder: event.carta.poder,
              estaOculta: event.carta.estaOculta,
              estaHabilitada: event.carta,
              url:event.carta.url
            });
          } 

          if (event.type === 'cardgame.cartaquitadadelmazo'){
            this.cartasDelJugador = this.cartasDelJugador
            .filter((item) =>item.cartaId !== event.carta.cartaId.uuid)
          } 

          if (event.type === 'cardgame.tiempocambiadodeltablero') {
            this.tiempo = event.tiempo;
          }
          if(event.type === 'cardgame.rondainiciada'){
            this.roundStarted = true;
          }  

          if(event.type === 'cardgame.rondaterminada'){
            this.roundStarted = false;
           
          }

            
        }
    })

 
    })

    this.juegoService$.getTablero(this.juegoId).subscribe((event)=>{
     
    this.tiempo = event.tiempo;
    this.jugadoresRonda = event.tablero.jugadores.length;
    this.jugadoresTablero = event.tablero.jugadores.length;
    this.numeroRonda = event.ronda.numero;   
  });

    
  }
    iniciarRonda(){
      this.ws.conection(this.juegoId).subscribe(data => console.log(data));
      this.juegoService$.iniciarRonda({
        juegoId: this.juegoId,
  
      }).subscribe();
      
    } 



    ponerCarta(cardId:string){
      this.juegoService$.ponerCartaEnTablero({
        juegoId:this.juegoId,
        cartaId:cardId,
        jugadorId: this.uid
      }).subscribe(e=>console.log(e))
    }
}