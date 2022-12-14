import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/modules/shared/services/auth.service';


@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.scss']
})
export class LogInComponent implements OnInit {
  //spin: boolean = true;

  constructor(
    private auth$: AuthService
  ) { }

  ngOnInit(): void {
  }

  btnLogin(): void {
    console.log('login button clicked');
    this.auth$.SigninWithGoogle(); 
  }

}
