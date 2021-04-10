import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { authInterceptorProviders } from './auth/auth.interceptor';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import {MatSelectModule} from '@angular/material/select';
import {MatRadioModule} from '@angular/material/radio';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatListModule} from '@angular/material/list';
import { AvatarModule } from 'ngx-avatar';
import { FindFriendsComponent } from './find-friends/find-friends.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { FriendsComponent } from './friends/friends.component';
import { ProfilesComponent } from './profiles/profiles.component';
import { PostsComponent } from './posts/posts.component';
import {MatGridListModule} from '@angular/material/grid-list';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { CommentsComponent } from './comments/comments.component';
import { ChatComponent } from './chat/chat.component';
import { PrivatechatComponent } from './chat/privatechat/privatechat.component';
import {MatDialogModule} from '@angular/material/dialog';
import { EditDialogComponent } from './profile/edit-dialog/edit-dialog.component';
import { NotificationComponent } from './notification/notification.component';
import { RequestsComponent } from './requests/requests.component';





@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    FindFriendsComponent,
    FriendsComponent,
    ProfilesComponent,
    PostsComponent,
    CommentsComponent,
    ChatComponent,
    PrivatechatComponent,
    EditDialogComponent,
    NotificationComponent,
    RequestsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatTabsModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatToolbarModule,
    FlexLayoutModule,
    MatSelectModule,
    MatRadioModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatSidenavModule,
    MatListModule,
    AvatarModule,
    MatPaginatorModule,
    MatGridListModule,
    InfiniteScrollModule,
    ReactiveFormsModule,
    MatDialogModule
  ],
  providers: [
    authInterceptorProviders,
    MatDatepickerModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
