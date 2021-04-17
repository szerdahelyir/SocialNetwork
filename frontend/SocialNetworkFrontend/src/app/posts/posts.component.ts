import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PostService } from '../services/post.service';
import { TokenService } from '../services/token.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {
  @Input() id: any;
  originalposts: any;
  posts: any[];
  currpostid: any;
  form: FormGroup = new FormGroup({});
  canPost: boolean = true;
  @ViewChild('postinput') input;

  constructor(private postService: PostService,
    private fb: FormBuilder,
    private token: TokenService) { }

  ngOnInit(): void {
    if (this.token.getUser().id == this.id) {
      this.getMyPosts();
    }
    else if (this.id == null) {
      this.getFriendsPosts();
    }
    else {
      this.canPost = false;
      this.getPostOfUser();
    }
    this.form = this.fb.group({
      content: [''],
    })
  }

  private getMyPosts() {
    this.postService.getMyPosts()
      .subscribe((data: any) => {
        this.originalposts = data;
        this.posts = data.slice(0, 10);
      }, error => {
        console.log(error);
      });
  }

  private getFriendsPosts() {
    this.postService.getFriendsPosts()
      .subscribe((data: any) => {
        this.originalposts = data;
        this.posts = data.slice(0, 10);
      }, error => {
        console.log(error);
      });
  }

  private getPostOfUser() {
    this.postService.getPostsOfUser(this.id)
      .subscribe((data: any) => {
        this.originalposts = data;
        this.posts = data.slice(0, 10);
      }, error => {
        console.log(error);
      });
  }

  onScrollDown() {
    if (this.posts.length < this.originalposts.length) {
      let len = this.posts.length;

      let toshowitem = this.originalposts.length - len > 2 ? this.originalposts.length : this.originalposts.length - len;

      for (let i = len; i < len + toshowitem; i++) {
        this.posts.push(this.originalposts[i]);
      }
    }
  }

  submit() {
    this.input.nativeElement.value = ' ';
    this.postService.addPost(this.form.value).subscribe();
    window.location.reload();
  }

  setPostId(id) {
    this.currpostid = id;
  }

  keyDownFunction(event) {
    if (event.keyCode === 13) {
      event.preventDefault();
      this.submit();
    }
  }
}
