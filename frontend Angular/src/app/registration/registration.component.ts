import { Component, OnInit } from '@angular/core';
import {AuthenticationSeviceService} from '../services/authentication-sevice.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  message:string;

  constructor(private autheService:AuthenticationSeviceService,
              private router:Router) { }

  ngOnInit(): void {
  }

  onRegister(value: any) {
    this.autheService.register(value).subscribe(data=>{
      this.router.navigateByUrl("/login");
    },error => {
      this.message=error.error.message;
    });
  }
}
