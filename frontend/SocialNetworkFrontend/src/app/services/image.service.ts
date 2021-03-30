import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from "rxjs/operators"; 


@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http:HttpClient) { }

  getImg(id) {
    return this.http.get('http://localhost:8080/api/images/get/' + id)
    .pipe(map((x : any)=> {
      const temp='data:image/jpeg;base64,' + x.picByte;
      return temp;
    }));
  }
}
