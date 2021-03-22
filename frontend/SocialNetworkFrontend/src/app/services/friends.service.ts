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

  addFriend(id): Observable<any> {
    return this.http.post(FRIENDS_API + 'add/' + id , null);
  }

  getFriends(request){
    const params=request;
    return this.http.get(FRIENDS_API + 'friends',{params});
  }

  acceptFriendRequest(id):Observable<any>{
    return this.http.put(FRIENDS_API + 'accept/' + id,null);
  }


}
