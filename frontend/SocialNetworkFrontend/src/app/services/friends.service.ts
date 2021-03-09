import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const FRIENDS_API = 'http://localhost:8080/api/relationships/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' })
};

@Injectable({
  providedIn: 'root'
})
export class FriendsService {

  constructor(private http:HttpClient) { }

  login(credentials): Observable<any> {
    return this.http.post(FRIENDS_API + 'add', {
      id: credentials.id
    },httpOptions);
  }
}
