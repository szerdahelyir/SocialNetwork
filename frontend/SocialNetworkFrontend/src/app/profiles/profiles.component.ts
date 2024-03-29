import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { FriendsService } from '../services/friends.service';
import { TokenService } from '../services/token.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit {
  id:any;
  user:any;

  constructor(private route: ActivatedRoute,
              private userService:UserService,
              private tokenService:TokenService,
              private router:Router,
              private friendsService:FriendsService) { }

  ngOnInit(): void {
    this.route.params
      .subscribe(
        (params: Params) => {
          this.id = +params['id'];
        }
      );
      if(this.id==this.tokenService.getUser().id){
        this.router.navigate(['/profile']);
      }
      this.userService.getUser(this.id)
      .subscribe((data: any)=>{
        this.user=data;
        
      },
      error=>{
        this.router.navigate(['/home']);
      });   
    }

    age(dob){
      var timeDiff = Math.abs(Date.now() - new Date(dob).getTime());
      return Math.floor(timeDiff / (1000 * 3600 * 24) / 365.25);
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
