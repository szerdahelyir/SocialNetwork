import { Component } from '@angular/core';
import { TokenService } from './services/token.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private roles: string[];
  isLoggedIn = false;
  email: string;

  constructor(private tokenStorageService: TokenService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.email = user.email;
    }
  }

  asd(){
    console.log(this.tokenStorageService.getUser());
    console.log(this.email);
  }
  logout(): void {
    this.tokenStorageService.signOut();
    window.location.reload();
  }
}
