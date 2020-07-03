import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {JwtHelper} from 'angular2-jwt';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationSeviceService {
  private host: String = 'http://localhost:8082';
  public jwtToken: string;
  public roles: Array<any>;

  constructor(private http: HttpClient) {
  }

  register(user){
    return this.http.post(this.host+"/register",user,
      {observe: 'response'});
  }

  login(user) {
    return this.http.post(this.host + '/login', user, {observe: 'response'});
  }

  saveToken(jwt: string) {
    this.jwtToken = jwt;
    localStorage.setItem('token', jwt);
    this.getRoles();
  }

  getToken() {
    return localStorage.getItem('token');
  }

  loadToken() {
    this.jwtToken = localStorage.getItem('token');
  }

  getRoles(){
    let jwtHelper = new JwtHelper();
    this.roles = jwtHelper.decodeToken(this.jwtToken).roles;
  }

  getTasks() {
    if (this.jwtToken == null) {
      this.loadToken();
    }
    return this.http.get(this.host + '/tasks',
      {headers: new HttpHeaders({'authorization': this.jwtToken})});
  }

  logOut() {
    this.jwtToken = null;
    localStorage.removeItem('token');
  }

  isAdmin() {
    if (this.roles==null){this.getRoles()}
    for (let role of this.roles) {
      if (role.authority == 'ADMIN') {
        return true;
        break;
      }
    }
    return false;
  }

  saveTask(task) {
    return this.http.post(
      this.host + '/tasks',
      task,
      {headers: new HttpHeaders({'authorization': this.jwtToken})}
    );
  }

  isAuthenticated() {
    if (this.jwtToken = this.getToken()) {
      return  true;
    }
    return false;
  }

}
