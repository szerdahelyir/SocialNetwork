import { Component } from '@angular/core';
import { MessageService } from './services/message.service';
import { TokenService } from './services/token.service';
import {SockJS} from 'sockjs-client';
import {Stomp} from 'stompjs';
import { UserService } from './services/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import { NotificationComponent } from './notification/notification.component';

declare var SockJS;
declare var Stomp;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  isLoggedIn = false;
  private ws;
  currentUser: any;
  curruser: any;

  constructor(private tokenStorageService: TokenService,
    private messageService:MessageService,
    private userService: UserService,
    private snackBar:MatSnackBar) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    

    if (this.isLoggedIn) {
      this.currentUser = this.tokenStorageService.getUser();
      this.userService.getUser(this.currentUser.id).subscribe((data: any)=>{
      this.curruser=data;
    });
      this.connect();
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
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
      (data=>{
        this.onMessageReceived(data)
      }) 
    );
  };

  onError = (err) => {
    console.log(err);
  };

  onMessageReceived(msg){
    const temp=JSON.parse(msg.body);
    console.log(temp.messageId);
    console.log(temp);
    this.messageService.getMessage(temp.messageId).subscribe(data=>{
      console.log(data);
      this.openSnackBar(data);
    })
  }
  openSnackBar(messageFrom) {
    this.snackBar.openFromComponent(NotificationComponent, {
      data:messageFrom,
      duration: 2000,
    });
  }
}
