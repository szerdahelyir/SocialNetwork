import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from "rxjs/operators"; 
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
    return this.http.get(FRIENDS_API + 'friends',{params}).pipe(map((x)=> {
      let temp : any=x['content'];
      for(let i of temp){
        if(i.profilePicture){
          i.profilePicture.picByte='data:image/jpeg;base64,' + i.profilePicture.picByte;
        }
      } 
      return x;
    }));
  }

  acceptFriendRequest(id):Observable<any>{
    return this.http.put(FRIENDS_API + 'accept/' + id,null);
  }


}
