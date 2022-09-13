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

  cartasDelJugador: Carta[] = [];
  cartasDelTablero: Carta[] = [];
  cartasJugadorTablero: string[] = []
  tiempo: number = 0;
  jugadoresRonda: number = 0;
  jugadoresTablero: number = 0;
  numeroRonda: number = 0;
  juegoId: string = "";
  uid: string = "";
  roundStarted:boolean = false;
  ganadorAlias:string = "";
  ganador:boolean = false;




  constructor(public juegoService$: JuegoServiceService,
    public authService: AuthService,
    public ws: WebSocketserviceTsService,
    private route: ActivatedRoute,
    private router: Router) { }


  /*ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.juegoId = params['id'];
      console.log(this.juegoId);
      this.uid = this.authService.user.uid;
      this.juegoService$.getMiMazo(this.uid, this.juegoId).subscribe((element:any) => {
      this.cartasDelJugador = element.cartas;
      });

      this.ws.conection(this.juegoId).subscribe({  
        next: (event:any) => {
                
          debugger;
          if (event.type === 'cardgame.tiempocambiadodeltablero') {
            this.tiempo = event.tiempo;
          }
          if (event.type === 'cardgame.rondacreada') {
            this.tiempo = event.tiempo;
            this.jugadoresRonda = event.ronda.jugadores.length;
            this.numeroRonda = event.ronda.numero;
          }

          if(event.type === 'cardgame.rondainiciada'){
            this.roundStarted = false;
          }

        },
        error: (err:any) => console.log(err),
        complete: () => console.log('complete')
      });
    
    });

    this.juegoService$.getTablero(this.juegoId).subscribe((element) => {
      this.cartasDelTablero = Object.entries(element.tablero.cartas).map((a: any) => {
        return a[1];
      });
      this.tiempo = element.tiempo;
      this.jugadoresRonda = element.ronda.jugadores.length;
      this.jugadoresTablero = element.tablero.jugadores.length;
      this.numeroRonda = element.ronda.numero;
    });
  }


  iniciarRonda(){
    this.ws.conection(this.juegoId).subscribe(data => console.log(data));
    this.juegoService$.iniciarRonda({
      juegoId: this.juegoId

    }).subscribe( x => console.log(x));
  }*/
  ngOnInit(){
    this.route.params.subscribe((params) => {
      this.juegoId = params['id'];

     
      
      this.ws.conection(this.juegoId).subscribe({
        next: (event:any) => {
          if (event.type === 'cardgame.tiempocambiadodeltablero') {
            this.tiempo = event.tiempo;
          }
          if(event.type === 'cardgame.rondainiciada'){
            this.roundStarted = false;
          }



          //if(event.type==='cardgame.jugadoragregado')

       
        }
    })

    this.juegoService$.getTablero(this.juegoId).subscribe(event=>{
      this.tiempo = event.tiempo;
      this.jugadoresRonda = event.tablero.jugadores.length;
      this.jugadoresTablero = event.tablero.jugadores.length;
      this.numeroRonda = event.ronda.numero;

      //l
     

      event.tablero.jugadores.forEach(id => {
        this.juegoService$.getMazoPorJugador(id).subscribe(mazo => {console.log("mazo:", mazo);})
      })
     
     
    });
    })

   

    
  }
    iniciarRonda(){
    //  this.ws.conection(this.juegoId).subscribe();
      this.juegoService$.iniciarRonda({
        juegoId: this.juegoId,
  
      }).subscribe();
      
    } 

/*btnLogout(): void{
  this.auth$.logout();*/



}
