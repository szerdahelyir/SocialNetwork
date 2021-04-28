import { Component, ElementRef, Input, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PostService } from '../services/post.service';
import { TokenService } from '../services/token.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  @Input() postId: any;
  originalComments: any[];
  comments: any[];
  form: FormGroup = new FormGroup({});
  @ViewChild('target') private myScrollContainer: ElementRef;
  @ViewChild('commentinput') input;

  constructor(private postService: PostService,
    private fb: FormBuilder,
    private token: TokenService) { }

  ngOnInit(): void {
    this.getComments();
    this.form = this.fb.group({
      content: [''],
    })
  }

  ngOnChanges(changes: SimpleChanges) {
    this.getComments();
    this.scrollToElement(this.myScrollContainer);
  }

  private getComments() {
    this.postService.getCommentOfPost(this.postId)
      .subscribe((data: any) => {
        this.comments = data;
        this.scrollToElement(this.myScrollContainer);

      }, error => {
        console.log(error);
      });
  }

  submit() {
    this.input.nativeElement.value = ' ';
    this.postService.addComment(this.form.value, this.postId).subscribe(data => {
      this.getComments();
      this.scrollToElement(this.myScrollContainer);
    });
  }

  keyDownFunction(event) {
    if (event.keyCode === 13) {
      event.preventDefault();
      this.submit();
    }
  }

  scrollToElement(el): void {
    this.myScrollContainer.nativeElement.scroll({
      top: this.myScrollContainer.nativeElement.scrollHeight,
      left: 0,
      behavior: 'smooth'
    });
  }

  delete(id){
    if (confirm('Are you sure?')) {
      this.postService.deleteComment(id).subscribe(data => {
        console.log(data)
        this.getComments();
      });
    }
  }

}
