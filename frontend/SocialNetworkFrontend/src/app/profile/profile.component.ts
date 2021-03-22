import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Source } from 'ngx-avatar/lib/sources/source';
import { TokenService } from '../services/token.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser: any;
  curruser: any;
  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;
  imgtemp:any;
  asdd:any;

  constructor(private token: TokenService,
              private userService: UserService,
              private http:HttpClient) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.userService.getUser(this.currentUser.id).subscribe((data: any)=>{
      this.curruser=data;
      this.getImg(this.curruser.profilePicture.id);
      console.log(this.curruser);  
    });
    
  }

  n
  asd(){
    console.log(this.curruser);
    this.getImg(this.curruser.profilePicture.id);   
  }

   //Gets called when the user selects an image
   public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];
  }
  //Gets called when the user clicks on submit to upload the image
  onUpload() {
    console.log(this.selectedFile);
    
    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile);
  
    //Make a call to the Spring Boot Application to save the image
    this.http.post('http://localhost:8080/api/images/upload', uploadImageData, { observe: 'response' })
      .subscribe((response) => {
        if (response.status === 200) {
          this.message = 'Image uploaded successfully';
        } else {
          this.message = 'Image not uploaded successfully';
        }
      }
    );
  }

  getImg(id){
    this.http.get('http://localhost:8080/api/images/get/' + id).subscribe((data)=>{
      this.asdd=data;
      this.imgtemp='data:image/jpeg;base64,' + this.asdd.picByte;
    }
    )
  }

}
