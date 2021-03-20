import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const POSTS_API = 'http://localhost:8080/api/posts/';
const COMMENT_API = "http://localhost:8080/api/comments/"

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' })
};

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http:HttpClient) { }

  getMyPosts(){
    return this.http.get(POSTS_API + 'myposts');
  }

  getFriendsPosts(){
    return this.http.get(POSTS_API + 'friends');
  }

  getPostsOfUser(userId){
    return this.http.get(POSTS_API + userId);
  }

  getCommentOfPost(postId){
    return this.http.get(COMMENT_API + postId);
  }

  addPost(post):Observable<any>{
    return this.http.post(POSTS_API,{
      content:post.content
    });
  }

  addComment(comment,postid):Observable<any>{
    return this.http.post(COMMENT_API + postid,{
      comment:comment.content
    });
  }
}
