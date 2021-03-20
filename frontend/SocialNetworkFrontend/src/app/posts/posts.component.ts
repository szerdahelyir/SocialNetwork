import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PostService } from '../services/post.service';
import { TokenService } from '../services/token.service';

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
  form: FormGroup = new FormGroup({});
  canPost:boolean=true;
  postFlag:boolean=true;

  constructor(private postService:PostService,
              private fb:FormBuilder,
              private token: TokenService) { }

  ngOnInit(): void {
    if(this.token.getUser().id==this.id){
      this.getMyPosts();
    }
    else if(this.id==null){
      this.getFriendsPosts();
    }
    else{
      this.canPost=false;
      this.getPostOfUser();
    }
    this.form = this.fb.group({
      content: [''],
    })
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

  private getFriendsPosts() {
    this.postService.getFriendsPosts()
      .subscribe((data:any) => {
        this.originalposts = data;
        this.posts = data.slice(0,10);
      }, error => {
        console.log(error);
      });
  }

  private getPostOfUser() {
    this.postService.getPostsOfUser(this.id)
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

  get f(){
    return this.form.controls;
  }
    
  submit(){
    console.log(this.form.value);
    this.postService.addPost(this.form.value).subscribe(data => {
      console.log(data)
    });
    window.location.reload();
  }

  setPostId(id){
    this.currpostid=id;
  }
}
