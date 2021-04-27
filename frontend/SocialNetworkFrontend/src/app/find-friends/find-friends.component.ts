import { Component, OnInit } from '@angular/core';
import { FriendsService } from '../services/friends.service';
import { UserService } from '../services/user.service';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-find-friends',
  templateUrl: './find-friends.component.html',
  styleUrls: ['./find-friends.component.css']
})
export class FindFriendsComponent implements OnInit {

  users: any[];
  totalElements: number = 0;
  form: FormGroup = new FormGroup({});
  pageSize=2;

  constructor(private friendsService: FriendsService,
    private userService: UserService,
    private router: Router,
    private fb: FormBuilder) {
    this.form = this.fb.group({
      search: [''],
    })
  }

  ngOnInit(): void {
    this.getUsers({ page: "0", size: this.pageSize });
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

  private getSearchedUsers(request) {
    this.userService.getSearchedUsers(request)
      .subscribe(data => {
        this.users = data['content'];
        this.totalElements = data['totalElements'];
      }, error => {
        console.log(error);
      });
  }

  search() {
    this.getSearchedUsers({ page: "0", size: "10", name:this.form.value.search});
  }

  viewProfile(id) {
    console.log(id);
    this.router.navigate(['profiles', id])
  }

  nextPage(event: PageEvent) {
    const request = {};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    this.getUsers(request);
  }

  add(id) {
    this.friendsService.addFriend(id).subscribe(data => {
      console.log(data)
    });
    window.location.reload();
  }

  accept(id) {
    this.friendsService.acceptFriendRequest(id).subscribe(data => {
      console.log(data)
    });
    window.location.reload();
  }

  decline(id){
    this.friendsService.declineFriendRequest(id).subscribe(data => {
      console.log(data)
    });
    window.location.reload();
  }
}
