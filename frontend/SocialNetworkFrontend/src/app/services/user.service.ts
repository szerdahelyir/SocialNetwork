import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';


const USER_API = 'http://localhost:8080/api/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUser(id){
    return this.http.get(USER_API + id);
  }
}
