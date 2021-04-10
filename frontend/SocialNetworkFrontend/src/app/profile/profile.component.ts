import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ImageService } from '../services/image.service';
import { TokenService } from '../services/token.service';
import { UserService } from '../services/user.service';
import { EditDialogComponent } from './edit-dialog/edit-dialog.component';


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
    private http: HttpClient,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.currentUser = this.token.getUser();
    this.getUser();
  }

  getUser() {
    this.userService.getUser(this.currentUser.id).subscribe((data: any) => {
      this.curruser = data;
      console.log(data);
    });
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(EditDialogComponent, {
      width: '250px',
      data: {
        firstName: this.curruser.firstName,
        lastName: this.curruser.lastName,
        location: this.curruser.location,
        description: this.curruser.description,
        dob: this.curruser.dob
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.curruser.firstName = result.firstName;
      this.curruser.lastName = result.lastName;
      this.curruser.location = result.location;
      this.curruser.dob = result.dob;
      this.curruser.description = result.description;
      this.userService.updateUser({
        firstName: result.firstName,
        lastName: result.lastName,
        description: result.description,
        location: result.location,
        dob: result.dob
      }, this.curruser.id).subscribe(data=>{
        console.log(data);
      })
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
          this.getUser();
        } else {
          this.message = 'Image not uploaded successfully';
        }
      }
      );
  }
}
