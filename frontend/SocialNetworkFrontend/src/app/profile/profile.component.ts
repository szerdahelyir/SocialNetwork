import { Component, OnInit } from '@angular/core';
import { TokenService } from '../services/token.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser: any;
  curruser: any;

  constructor(private token: TokenService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.userService.getUser(this.currentUser.id).subscribe((data: any)=>{
      this.curruser=data;
      console.log(this.curruser);
    });   
  }
}
