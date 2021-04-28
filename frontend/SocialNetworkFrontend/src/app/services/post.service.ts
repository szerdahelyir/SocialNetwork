import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from "rxjs/operators";

const POSTS_API = 'http://localhost:8080/api/posts/';
const COMMENT_API = "http://localhost:8080/api/comments/"

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' })
};

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  getMyPosts() {
    return this.http.get(POSTS_API + 'myposts').pipe(map((x: any) => {
      for (let i of x) {
        if (i.userDTO.profilePicture) {
          i.userDTO.profilePicture.picByte = 'data:image/jpeg;base64,' + i.userDTO.profilePicture.picByte;
        }
      }
      return x;
    }))
  }

  getFriendsPosts() {
    return this.http.get(POSTS_API + 'friends').pipe(map((x: any) => {
      for (let i of x) {
        if (i.userDTO.profilePicture) {
          i.userDTO.profilePicture.picByte = 'data:image/jpeg;base64,' + i.userDTO.profilePicture.picByte;
        }
      }
      return x;
    }))
  }

  getPostsOfUser(userId) {
    return this.http.get(POSTS_API + userId).pipe(map((x: any) => {
      for (let i of x) {
        if (i.userDTO.profilePicture) {
          i.userDTO.profilePicture.picByte = 'data:image/jpeg;base64,' + i.userDTO.profilePicture.picByte;
        }
      }
      return x;
    }))
  }

  getCommentOfPost(postId) {
    return this.http.get(COMMENT_API + postId).pipe(map((x: any) => {
      for (let i of x) {
        if (i.userDTO.profilePicture) {
          i.userDTO.profilePicture.picByte = 'data:image/jpeg;base64,' + i.userDTO.profilePicture.picByte;
        }
      }
      return x;
    }))
  }

  addPost(post): Observable<any> {
    return this.http.post(POSTS_API, {
      content: post.content
    });
  }

  addComment(comment, postId): Observable<any> {
    return this.http.post(COMMENT_API + postId, {
      comment: comment.content
    });
  }

  deleteComment(commentId): Observable<any> {
    return this.http.delete(COMMENT_API + commentId);
  }

  deletePost(postId): Observable<any> {
    return this.http.delete(POSTS_API + postId);
  }
}
