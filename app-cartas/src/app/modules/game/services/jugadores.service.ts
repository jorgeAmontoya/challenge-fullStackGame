import { Injectable } from '@angular/core';
import { jugadoresModel } from '../models/jugadores.model';

@Injectable({
  providedIn: 'root'
})
export class JugadoresService {

  constructor() { }

  getJugadores(): Array<jugadoresModel>{
    const jugadores= new Array<jugadoresModel>();
    jugadores.push({
      name: 'Camilo',
      id:'1'
    });
    jugadores.push({
      name: 'Luisa',
      id:'2'
    });
    jugadores.push({
      name: 'Sebastiasn',
      id:'3'
    });
    jugadores.push({
      name: 'Jorge',
      id:'4'
    });
    return jugadores;
    
  }
}
