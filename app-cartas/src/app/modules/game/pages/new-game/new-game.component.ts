//controlador
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { jugadoresModel } from '../../models/jugadores.model';
import { JugadoresService } from '../../services/jugadores.service';

@Component({
  selector: 'app-new-game',
  templateUrl: './new-game.component.html',
  styleUrls: ['./new-game.component.scss']
})
export class NewGameComponent implements OnInit {

  frmJugadores: FormGroup;
  jugadores!: Array<jugadoresModel>;

  constructor(private jugadores$: JugadoresService) { 
    this.frmJugadores = this.createFormJugadores();
  }


  ngOnInit(): void {
    this.jugadores = this.jugadores$.getJugadores();
    console.log(this.jugadores);
     
  }

  public submit(): void {
    console.log("Submit", this.frmJugadores.getRawValue()) ;
  }

  private createFormJugadores(): FormGroup {
    return new FormGroup({
      jugadores: new FormControl(null, [Validators.required])
    });
  }

}
