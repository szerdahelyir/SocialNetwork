import { Component, OnInit } from '@angular/core';
import { FriendsService } from '../services/friends.service';
import { TokenService } from '../services/token.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-find-friends',
  templateUrl: './find-friends.component.html',
  styleUrls: ['./find-friends.component.css']
})
export class FindFriendsComponent implements OnInit {

  users: any[];

  constructor(private friendsService: FriendsService,
              private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe((data: any)=>{
      this.users=data;
      console.log(this.users);
      /* var idToDelete = this.token.getUser().id;
      this.users = this.users.filter(item=>item.id !=idToDelete );
      console.log(this.users); */
    });
  }

  test(id){
    console.log(id);
  }
}
