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

  age(dob){
    var timeDiff = Math.abs(Date.now() - new Date(dob).getTime());
    return Math.floor(timeDiff / (1000 * 3600 * 24) / 365.25);
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

  public onFileChanged(event) {
    this.selectedFile = event.target.files[0];
    this.onUpload();
  }
  onUpload() {
    console.log(this.selectedFile);

    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile);

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
