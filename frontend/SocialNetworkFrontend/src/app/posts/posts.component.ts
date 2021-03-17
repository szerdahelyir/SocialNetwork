import { Component, Input, OnInit } from '@angular/core';
import { PostService } from '../services/post.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  @Input() id:any;
  posts:any[];

  constructor(private postService:PostService) { }

  ngOnInit(): void {
    this.getMyPosts();
    console.log(this.posts);
  }

  private getMyPosts() {
    this.postService.getMyPosts()
      .subscribe((data:any) => {
        this.posts=data;
      }, error => {
        console.log(error);
      });
  }
  asd(){
    console.log(this.posts)
  }
}
