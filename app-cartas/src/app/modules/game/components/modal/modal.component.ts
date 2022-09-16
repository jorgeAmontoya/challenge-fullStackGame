import { Component, Input, OnInit } from '@angular/core';
import { getAuth } from 'firebase/auth';
import { JuegoServiceService } from '../../services/juego-service.service';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {
 jugadores: any[] = [];
  @Input() jugadoresEnLaRonda: Array<any>  = [];

  constructor(private juegoservice$: JuegoServiceService) { 
  }

  ngOnInit(): void {
    this.jugadores = this.jugadoresEnLaRonda.filter(id => id !== getAuth().currentUser?.uid);
  }
cerrar(){
  this.juegoservice$.showModal.emit(false);

}

}
