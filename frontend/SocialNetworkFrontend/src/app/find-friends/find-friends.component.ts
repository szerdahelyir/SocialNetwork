import { Component, OnInit } from '@angular/core';
import { FriendsService } from '../services/friends.service';
import { UserService } from '../services/user.service';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';

@Component({
  selector: 'app-find-friends',
  templateUrl: './find-friends.component.html',
  styleUrls: ['./find-friends.component.css']
})
export class FindFriendsComponent implements OnInit {

  users: any[];
  totalElements: number = 0;

  constructor(private friendsService: FriendsService,
              private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.getUsers({ page: "0", size: "10" });
  }

  private getUsers(request) {
    this.userService.getUsers(request)
      .subscribe(data => {
        this.users = data['content'];
        this.totalElements = data['totalElements'];
      }, error => {
        console.log(error);
      });
  }

  viewProfile(id){
    console.log(id);
    this.router.navigate(['profiles', id])
  }

  nextPage(event: PageEvent) {
    const request = {};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    this.getUsers(request);
  }

  add(id){
    this.friendsService.addFriend(id).subscribe(data => {
      console.log(data)
    });
    window.location.reload();
  }

  accept(id){
    this.friendsService.acceptFriendRequest(id).subscribe(data => {
      console.log(data)
    });
    window.location.reload();
  }
}
