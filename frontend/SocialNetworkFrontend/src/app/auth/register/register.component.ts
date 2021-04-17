import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: any = {};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  minDate = new Date(1900, 0, 1);
  maxDate = new Date();

  constructor(private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.log(this.form);
    this.authService.register(this.form).subscribe(
      data => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.router.navigate(['/login']);
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

}
