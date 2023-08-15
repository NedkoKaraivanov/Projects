import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { MaterialModule } from './material/material.module';
import { UserModule } from './user/user.module';
import { HomeComponent } from './home/home.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticateComponent } from './authenticate/authenticate.component';
import { jwtInterceptorProvider } from './jwt.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    WelcomeComponent,
    AuthenticateComponent
  ],
  imports: [
    BrowserModule,
    CoreModule,
    SharedModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    UserModule,
    AppRoutingModule,
  ],
  providers: [jwtInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
