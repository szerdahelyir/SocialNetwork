import { Component, Input, OnInit } from '@angular/core';
import { PostService } from '../services/post.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  @Input() id:any;
  originalposts:any;
  posts:any[];
  currpostid:any;

  constructor(private postService:PostService) { }

  ngOnInit(): void {
    this.getMyPosts();
    console.log(this.posts);
  }

  private getMyPosts() {
    this.postService.getMyPosts()
      .subscribe((data:any) => {
        this.originalposts = data;
        this.posts = data.slice(0,10);
      }, error => {
        console.log(error);
      });
  }
  asd(){
    console.log(this.posts)
  }
  onScrollDown(){
    if(this.posts.length < this.originalposts.length){  
      let len = this.posts.length;

      let toshowitem = this.originalposts.length-len>2?2:this.originalposts.length-len;
 
      for(let i = len; i < len+toshowitem; i++){
        this.posts.push(this.originalposts[i]);
      }
    }
  }

  setPostId(id){
    this.currpostid=id;
  }
}
