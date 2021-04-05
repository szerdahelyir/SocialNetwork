import { Component, OnInit } from '@angular/core';
import { MessageService } from '../services/message.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  originalChats:any[];
  chats:any[];
  currId:number;

  constructor(private messageService:MessageService) { 
    this.messageService.getChats().subscribe(data=>{
      this.originalChats = data;
        console.log(data);
        this.chats = data.slice(0,10);
    })
  }

  ngOnInit(): void {
  }
  connect(){
    this.messageService.connect();
  }

  sendMessage(){
    this.messageService.sendMessage();
    console.log(this.chats);
  }

  setId(id){
    this.currId=id;
    console.log(id);
  }

  onScrollDown(){
    if(this.chats.length < this.originalChats.length){  
      let len = this.chats.length;

      let toshowitem = this.originalChats.length-len>2?2:this.originalChats.length-len;
 
      for(let i = len; i < len+toshowitem; i++){
        this.chats.push(this.originalChats[i]);
      }
    }
  }
}
