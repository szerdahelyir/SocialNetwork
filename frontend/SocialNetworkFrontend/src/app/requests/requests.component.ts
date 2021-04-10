import { Component, OnInit } from '@angular/core';
import { FriendsService } from '../services/friends.service';

@Component({
  selector: 'app-requests',
  templateUrl: './requests.component.html',
  styleUrls: ['./requests.component.css']
})
export class RequestsComponent implements OnInit {
  requests: [];

  constructor(private friendsService:FriendsService) { }

  ngOnInit(): void {
    this.getFriendRequests();
  }
  
  getFriendRequests(){
    this.friendsService.getFriendRequests().subscribe((data:any)=>{
      this.requests=data;
      console.log(this.requests);
    });
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
