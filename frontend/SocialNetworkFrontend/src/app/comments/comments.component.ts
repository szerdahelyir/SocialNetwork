import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PostService } from '../services/post.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  @Input() postId:any;
  originalComments:any[];
  comments:any[];
  form: FormGroup = new FormGroup({});

  constructor(private postService:PostService,
              private fb:FormBuilder) { }

  ngOnInit(): void {
    this.getComments();
    this.form = this.fb.group({
      content: [''],
    })
  }

  ngOnChanges(changes: SimpleChanges){
    this.getComments();
  }

  private getComments() {
    this.postService.getCommentOfPost(this.postId)
      .subscribe((data:any) => {
        this.originalComments = data;
        this.comments = data.slice(0,10);
      }, error => {
        console.log(error);
      });
  }

  asd(){
    console.log(this.comments)
  }
  onScrollDown(){
    if(this.comments.length < this.originalComments.length){  
      let len = this.comments.length;

      let toshowitem = this.originalComments.length-len>2?2:this.originalComments.length-len;
 
      for(let i = len; i < len+toshowitem; i++){
        this.comments.push(this.originalComments[i]);
      }
    }
  }

  get f(){
    return this.form.controls;
  }
    
  submit(){
    console.log(this.form.value);
    this.postService.addComment(this.form.value,this.postId).subscribe(data => {
      console.log(data)
    });
    this.getComments();
  }

}
