import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ImageService } from '../services/image.service';
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
  message: string;
  teszt: any;

  constructor(private token: TokenService,
    private userService: UserService,
    private http: HttpClient) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.getUser();
  }

  getUser() {
    this.userService.getUser(this.currentUser.id).subscribe((data: any) => {
      this.curruser = data;
    });
  }

  //Gets called when the user selects an image
  public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];
    this.onUpload();
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
    window.location.reload();
  }
}
