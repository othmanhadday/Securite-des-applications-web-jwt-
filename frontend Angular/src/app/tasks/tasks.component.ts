import {Component, OnInit} from '@angular/core';
import {AuthenticationSeviceService} from '../services/authentication-sevice.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
  tasks:any;

  constructor(private authentication: AuthenticationSeviceService,
              private router: Router) {
  }

  ngOnInit(): void {
    if (this.authentication.isAuthenticated()) {
      this.authentication.getTasks().subscribe(res => {
        this.tasks = res;
        console.log(this.tasks);
      }, err => {
        this.authentication.logOut();
        this.router.navigateByUrl('/login');
      });
    }else {
      this.router.navigateByUrl('/login');
    }
  }

}
