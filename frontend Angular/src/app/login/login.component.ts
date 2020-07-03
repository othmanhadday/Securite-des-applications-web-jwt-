import { Component, OnInit } from '@angular/core';
import {AuthenticationSeviceService} from '../services/authentication-sevice.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  mode:number=0;
  constructor(private authentication:AuthenticationSeviceService,
              private router:Router) { }

  ngOnInit(): void {
    if(this.authentication.isAuthenticated()){
      this.router.navigateByUrl("/tasks");
    }
  }

  onLogin(value) {
    console.log(value);
    this.authentication.login(value).subscribe(resp=>{
      let jwt = resp.headers.get("authorization");
      this.authentication.saveToken(jwt);
      this.router.navigateByUrl("/tasks");
    },error => {
      this.mode=1;
    });
  }
}
