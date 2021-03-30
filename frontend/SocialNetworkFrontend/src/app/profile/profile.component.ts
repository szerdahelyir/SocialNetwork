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
  teszt:any;

  constructor(private token: TokenService,
    private userService: UserService,
    private http: HttpClient,
    private imageService:ImageService) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.userService.getUser(this.currentUser.id).subscribe((data: any) => {
      this.curruser = data;
      this.imageService.getImg(this.curruser.profilePicture.id).subscribe(data=>{
        console.log(data);
        this.teszt=data});
      console.log(this.curruser);
    });
    

  }

  asd() {
    console.log(this.teszt);
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

  getImg(id) {
    return this.http.get('http://localhost:8080/api/images/get/' + id).subscribe((data) => {
      const asdd: any = data;
      return 'data:image/jpeg;base64,' + asdd.picByte;
      }
    )
  }

  

}
