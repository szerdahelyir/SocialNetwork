import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from "rxjs/operators"; 

const USER_API = 'http://localhost:8080/api/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUser(id){
    return this.http.get(USER_API + id).pipe(map((x:any)=>{
      if(x.profilePicture){
        x.profilePicture.picByte='data:image/jpeg;base64,' + x.profilePicture.picByte;
      }
      return x;
    }));
  }

  getUsers(request){
    const params=request;
    return this.http.get(USER_API,{params}).pipe(map((x)=> {
      let temp : any=x['content'];
      for(let i of temp){
        if(i.profilePicture){
          i.profilePicture.picByte='data:image/jpeg;base64,' + i.profilePicture.picByte;
        }
      } 
      return x;
    }));
  }
}
