import { Component } from '@angular/core';
import {AuthenticationSeviceService} from './services/authentication-sevice.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'jwtFrontend';

  constructor(public authService:AuthenticationSeviceService,
              private router:Router) {
    if(!authService.isAuthenticated()){
      this.router.navigateByUrl("/login");
    }
  }

  logOut() {
    this.authService.logOut();
    this.router.navigateByUrl("/login");
  }
}
