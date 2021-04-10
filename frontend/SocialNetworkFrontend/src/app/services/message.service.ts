import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SockJS } from 'sockjs-client';
import { Stomp } from 'stompjs';
import { TokenService } from './token.service';
import { UserService } from './user.service';
import { map } from "rxjs/operators";

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
    private userService: UserService,
    private http: HttpClient) {
    if (this.token.getUser()) {
      this.currentUser = this.token.getUser();
      this.userService.getUser(this.currentUser.id).subscribe((data: any) => {
        this.curruser = data;
      });
    }
  }

  connect() {
    console.log(this.currentUser);
    let socket = new SockJS("http://localhost:8080/ws");
    this.ws = Stomp.over(socket);
    let that = this;
    this.ws.connect({ "Token": this.currentUser.token }, this.onConnected, this.onError);
  }

  onConnected = () => {
    console.log("connected");
    console.log("connected");
    console.log("connected");
    console.log("connected");
    console.log(this.currentUser);
    this.ws.subscribe(
      "/user/" + this.curruser.id + "/queue/messages",
      (data => {
        this.onMessageReceived(data)
      })
    );
  };

  onError = (err) => {
    console.log(err);
  };

  onMessageReceived(msg) {
    const temp = JSON.parse(msg.body);
    console.log(temp.messageId);
    console.log(temp);
    this.getMessage(temp.messageId).subscribe(data => {
      console.log(data);
    })
  }

  sendMessage(msg) {
    const message = {
      senderId: this.curruser.id,
      recipientId: msg.recipientId,
      message: msg.message,
    };
    this.ws.send("/app/chat", {}, JSON.stringify(message));
  }
  getChats() {
    return this.http.get('http://localhost:8080/chats').pipe(map((x: any) => {
      for (let i of x) {
        if (i.receiver.profilePicture) {
          i.receiver.profilePicture.picByte = 'data:image/jpeg;base64,' + i.receiver.profilePicture.picByte;
        }
      }
      return x;
    }))
  }

  getMessages(sender, recipient) {
    return this.http.get('http://localhost:8080/messages/' + sender + '/' + recipient).pipe(map((x: any) => {
      for (let i of x) {
        if (i.sender.profilePicture) {
          i.sender.profilePicture.picByte = 'data:image/jpeg;base64,' + i.sender.profilePicture.picByte;
        }
        if (i.receiver.profilePicture) {
          i.receiver.profilePicture.picByte = 'data:image/jpeg;base64,' + i.receiver.profilePicture.picByte;
        }
      }
      return x;
    }))
  }

  getMessage(id) {
    return this.http.get('http://localhost:8080/messages/' + id).pipe(map((x: any) => {
      if (x.sender.profilePicture) {
        x.sender.profilePicture.picByte = 'data:image/jpeg;base64,' + x.sender.profilePicture.picByte;
      }
      if (x.receiver.profilePicture) {
        x.receiver.profilePicture.picByte = 'data:image/jpeg;base64,' + x.receiver.profilePicture.picByte;
      }
      return x;
    }))
  }
}
