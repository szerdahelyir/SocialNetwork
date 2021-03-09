import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { AuthGuard } from './auth.guard';
import { FindFriendsComponent } from './find-friends/find-friends.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full', canActivate: [AuthGuard]},
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  { path: 'find-friends', component: FindFriendsComponent, canActivate: [AuthGuard]}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
