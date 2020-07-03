import {Component, OnInit} from '@angular/core';
import {AuthenticationSeviceService} from '../services/authentication-sevice.service';
import {Route, Router} from '@angular/router';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css']
})
export class NewTaskComponent implements OnInit {
  task;
  mode: number = 0;

  constructor(private authentication: AuthenticationSeviceService,
              private router:Router) {
  }

  ngOnInit(): void {
  }

  onSaveTask(value) {
    if (!this.authentication.isAuthenticated()) {
      this.router.navigateByUrl('/login');
    } else {
      this.authentication.saveTask(value).subscribe(data => {
        this.task = data;
        this.mode = 1;
      }, error => {
        this.mode = 0;
      });
    }
  }

}
