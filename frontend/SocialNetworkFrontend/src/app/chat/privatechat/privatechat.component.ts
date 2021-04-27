import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
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
  form: FormGroup = new FormGroup({});
  @ViewChild('target') private myScrollContainer: ElementRef;
  @ViewChild('msginput') input;

  constructor(private token: TokenService,
    private userService: UserService,
    private http: HttpClient,
    private messageService: MessageService,
    private fb:FormBuilder) { }

  ngOnInit(): void {
    this.currentUserId = this.token.getUser().id;
    this.getMessages();
    setInterval(()=>{
      this.getMessages();
    },3000);
    this.form = this.fb.group({
      message: [''],
    })
    this.messageService.connect();
  }

  ngOnChanges(changes: SimpleChanges) {
    this.getMessages();
  }

  private getMessages() {
    this.messageService.getMessages(this.currentUserId, this.id).subscribe(data => {
      this.messages = data;
      this.scrollToElement(this.myScrollContainer);
    })
  }
  keyDownFunction(event) {
    if (event.keyCode === 13) {
      event.preventDefault();
      this.submit();
    }
  }

  submit(){
    this.input.nativeElement.value = ' ';
    this.messageService.sendMessage({recipientId:this.id, message:this.form.value.message});
    this.form.value.message='';
  }

  onMessageReceived(msg){
    const temp=JSON.parse(msg.body);
    console.log(temp.messageId);
    console.log(temp);
    this.messageService.getMessage(temp.messageId).subscribe(data=>{
      console.log(data);
    })
  }

  scrollToElement(el): void {
    this.myScrollContainer.nativeElement.scroll({
      top: this.myScrollContainer.nativeElement.scrollHeight,
      left: 0,
      behavior: 'smooth'
    });
  }
}
