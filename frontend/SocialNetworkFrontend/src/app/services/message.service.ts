import { Injectable } from '@angular/core';
import {SockJS} from 'sockjs-client';
import {Stomp} from 'stompjs';
import { TokenService } from './token.service';
import { UserService } from './user.service';

declare var SockJS;
declare var Stomp;


@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private ws;
  currentUser: any;
  curruser: any;

  constructor(private token: TokenService,
    private userService: UserService,) { 
      this.currentUser = this.token.getUser();
      this.userService.getUser(this.currentUser.id).subscribe((data: any)=>{
      this.curruser=data;
    });
    }

  connect(){
    console.log(this.currentUser);
    let socket = new SockJS("http://localhost:8080/ws");
    this.ws = Stomp.over(socket);
    let that = this;
    this.ws.connect({"Token": this.currentUser.token}, this.onConnected, this.onError);
  }

  onConnected = () => {
    console.log("connected");
    console.log(this.currentUser);
    this.ws.subscribe(
      "/user/" + this.curruser.id + "/queue/messages",
      console.log("asd")
    );
  };

  onError = (err) => {
    console.log(err);
  };
}
