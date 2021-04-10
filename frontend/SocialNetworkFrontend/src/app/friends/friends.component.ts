import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { FriendsService } from '../services/friends.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css']
})
export class FriendsComponent implements OnInit {

  users: any[];
  totalElements: number = 0;
  pageSize:number=2;

  constructor(private friendsService: FriendsService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.getFriends({ page: "0", size: this.pageSize });
  }

  private getFriends(request) {
    this.friendsService.getFriends(request)
      .subscribe(data => {
        console.log(data);
        this.users = data['content'];
        this.totalElements = data['totalElements'];
      }, error => {
        console.log(error);
      });
  }
  asd(){
    console.log("asd");
    console.log(this.users);
  }

  viewProfile(id){
    console.log(id);
    this.router.navigate(['profiles', id])
  }

  nextPage(event: PageEvent) {
    const request = {};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    this.getFriends(request);
  }

}
