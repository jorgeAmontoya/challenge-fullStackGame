import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Carta } from 'src/app/modules/shared/models/mazo';
import { AuthService } from 'src/app/modules/shared/services/auth.service';
import { JuegoServiceService } from '../../services/juego-service.service';
import { WebSocketserviceTsService } from '../../services/web-socketservice.ts.service';
import swal from'sweetalert2';

@Component({
  selector: 'app-tablero',
  templateUrl: './tablero.component.html',
  styleUrls: ['./tablero.component.scss']
})
export class TableroComponent implements OnInit, OnDestroy {
// variables
  juegoId: string = "";
  uid: string = "";
  tiempo: number = 0;
  jugadoresRonda: number = 0;

  jugadoresTablero: number = 0;
  numeroRonda: number = 0;
 // roundStarted:boolean = false;
  cartasDelJugador: Carta[] = [];
  cartasDelTablero: Carta[] = [];
  
  ganadorRonda: string = "";
  //cartasJugadorTablero: string[] = []
  ganadorAlias:string = "";
  ganador:boolean = false;
  jugadorSeleccionado: string =""
  mostrarModal:boolean = true;
  jugadoresEnLaRonda: any[] = new Array<any>();
  
  arregloSeleccionado: any[] = new Array<any>();

  // constructor
  constructor(public juegoService$: JuegoServiceService,
    public authService: AuthService,
    public ws: WebSocketserviceTsService,
    private route: ActivatedRoute,
    private router: Router
    ) { }
  
  ngOnInit():void{
    this.route.params.subscribe((params) => {
    this.juegoId = params['id'];
    this.uid = this.authService.obtenerUsuarioSesion().uid;
    this.getMazo();
    this.getTablero()
    })
    
    this.ws.conection(this.juegoId).subscribe({
      next: (event:any) => {

        if (event.type === 'cardgame.tiempocambiadodeltablero') {
          this.tiempo = event.tiempo;
        }
        if(event.type === 'cardgame.rondainiciada'){
         // this.roundStarted = true;
          this.tiempo = event.tiempo;
          this.numeroRonda=event.ronda.numero; 
          this.cartasDelJugador=this.cartasDelJugador;

        }  
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

        if (event.type === 'cardgame.rondacreada') {
          this.tiempo = event.tiempo;
          this.jugadoresRonda = event.ronda.jugadores.length
          this.numeroRonda=event.ronda.numero;
          this.jugadoresEnLaRonda = event.ronda.jugadores
          .filter((jugador: { uuid: string; }) => jugador.uuid != this.uid);            
        }

        if(event.type === 'cardgame.juegofinalizado') {
          this.ganadorAlias = "Ganador:" + event.alias;
          this.ganador = true;
          this.ganadorRonda=event.alias;
            swal.fire('ganador del juego',this.ganadorRonda);
            setTimeout(() => { 
              this.router.navigate(['listaJugadores']);
            },300);
        }

        if (event.type === 'cardgame.JugadorSeleccionado') {
          this.jugadorSeleccionado = event.jugadorSeleccionado;
        }

        if (event.type === 'cardgame.tiempocambiadodeltablero') {
          this.tiempo = event.tiempo;
          if (event.tiempo == 1 && this.numeroRonda >= 3 && this.jugadorSeleccionado == this.uid) {
            debugger;
            this.mostrarModal = true;
            
          }
        }

        if(event.type === 'cardgame.rondaterminada'){
          this.cartasDelTablero = [];
        }

        if(event.type === 'cardgame.cartasasignadasajugador'){
          if(event.ganadorId.uuid === this.uid){
            event.cartasApuesta.forEach((carta: any) => {
              this.cartasDelJugador.push({
                cartaId: carta.cartaId.uuid,
                poder: carta.poder,
                estaOculta: carta.estaOculta,
                estaHabilitada: carta.estaHabilitada,
                url: carta.url
              });
            });
            alert("Ganaste la ronda!")
          }else{
            alert("Perdiste la ronda :(")
        }
      }
            
    }
  })

 this.juegoService$.showModal.subscribe(
   event => this.mostrarModal = event.valueOf()
   

 )
}

ngOnDestroy(): void {
  this.ws.closeConexion();
}
getTablero(){
  this.juegoService$.getTablero(this.juegoId).subscribe((event)=>{
     
    this.tiempo = event.tiempo;
    this.jugadoresRonda = event.tablero.jugadores.length;
    this.jugadoresTablero = event.tablero.jugadores.length;
    this.numeroRonda = event.ronda.numero; 
    this.jugadoresEnLaRonda = event.ronda.jugadores;  
  })
}
  getMazo() {
    this.juegoService$.getMiMazo(this.uid, this.juegoId).subscribe((element: any) => {
      this.cartasDelJugador = element.cartas
      console.log(this.cartasDelJugador)
    })
  };
      
  limpiarTablero(){
    this.cartasDelTablero.length-=this.cartasDelTablero.length
  }
    
  iniciarRonda(){
    this.ws.conection(this.juegoId).subscribe(data => console.log(data));
    this.juegoService$.iniciarRonda({
      juegoId: this.juegoId,

    }).subscribe();
    
  } 

selecionarJugadores(id:string): void{

}

  Cerrar(){
    this.juegoService$.showModal.emit(false);
    debugger;

    if (this.arregloSeleccionado.length >=1 && this.arregloSeleccionado.length <=2)
    {
      //alert("Jugador potenciado",this.arregloSeleccionado);
      const body ={
        "juegoId": this.juegoId,
        "jugadoresSeleccionados": this.arregloSeleccionado,
        "jugadorPotenciado": this.uid
      }
      this.juegoService$.finalizarRonda(body);
     // alert("Jugador potenciado",body);
    }
    else
    {
      alert("Seleccione un jugador")
    }    
  }

  ponerCarta(cardId:string){
    this.juegoService$.ponerCartaEnTablero({
      juegoId:this.juegoId,
      cartaId:cardId,
      jugadorId: this.uid
    }).subscribe(e=>console.log(e))
  }
}