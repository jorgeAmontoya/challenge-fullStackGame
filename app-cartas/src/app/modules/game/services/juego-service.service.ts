import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JuegoModel22 } from '../models/juego.model'; 
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CrearRondaCommand } from '../../shared/commands/CrearRondaCommand';
import { IniciarJuegoCommand } from '../../shared/commands/IniciarJuegoCommand';
import { TableroModel } from '../../shared/models/tablero';
import { JuegoModel } from '../../shared/models/juego';
import { modeloJuego } from '../../shared/models/modeloJuego';

@Injectable({
  providedIn: 'root'
})
export class JuegoServiceService {

  @Output() showModal: EventEmitter<Boolean> = new EventEmitter();

  constructor(private http: HttpClient) { }

  public crearJuego(body:any){
    return this.http.post('http://localhost:8080/juego/crear/', {...body})
  }

  listarJuegos(idJugadorPrincipal: string | null):Observable<JuegoModel22[]>{
    return this.http.get<JuegoModel22[]>(`http://localhost:8080/juego/listar/${idJugadorPrincipal}`);
  }

  crearRonda(command: CrearRondaCommand){
    
    return this.http.post( 'http://localhost:8080/juego/crear/ronda', command);
    
  }

  iniciar(command: IniciarJuegoCommand){
    return this.http.post( 'http://localhost:8080/juego/iniciar', command);
  }


  
  getMiMazo(uid: string, juegoId: string) {
    return this.http.get('http://localhost:8080/juego/mazo/'+uid+'/'+juegoId);
   }

   getMazoPorJugador(uid:string){
    return this.http.get('http://localhost:8080/jugador/mazo/'+uid);

   }

  getTablero(juegoId: string): Observable<TableroModel> { 
    return this.http.get<TableroModel>('http://localhost:8080/juego/'+juegoId);
  }

  iniciarRonda(command: any){
    return this.http.post('http://localhost:8080/juego/ronda/iniciar/', command)
  }

  ponerCartaEnTablero(command: any){
    return this.http.post('http://localhost:8080/juego/poner', command)
  }

  getJuegos(): Observable<modeloJuego[]> {
    return this.http.get<modeloJuego[]>('http://localhost:8080/juegos/');
  }


}
