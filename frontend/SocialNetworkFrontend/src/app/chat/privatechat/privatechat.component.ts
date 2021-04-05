import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { MessageService } from 'src/app/services/message.service';
import { TokenService } from 'src/app/services/token.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-privatechat',
  templateUrl: './privatechat.component.html',
  styleUrls: ['./privatechat.component.css']
})
export class PrivatechatComponent implements OnInit {
  @Input() id: any;
  currentUserId: number;
  messages: [];

  constructor(private token: TokenService,
    private userService: UserService,
    private http: HttpClient,
    private messageService: MessageService) { }

  ngOnInit(): void {
    this.currentUserId = this.token.getUser().id;
    this.getMessages();
  }

  ngOnChanges(changes: SimpleChanges) {
    this.getMessages();
  }

  private getMessages() {
    this.messageService.getMessages(this.currentUserId, this.id).subscribe(data => {
      console.log(this.currentUserId, this.id);
      console.log(data);
      this.messages = data;
    })
  }
  asd() {
    this.getMessages();
  }
  onScrollUp(){
    console.log("fel");
  }
  onScroll(){
    console.log("asd");
  }
}
