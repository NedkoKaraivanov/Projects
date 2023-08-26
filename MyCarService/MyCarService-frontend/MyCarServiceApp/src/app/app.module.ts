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
import { ToastrModule } from 'ngx-toastr';
import { AdminModule } from './admin/admin.module';
import { NotFoundComponent } from './not-found/not-found.component';
import { ServicesPageComponent } from './services-page/services-page.component';
import { AboutPageComponent } from './about-page/about-page.component';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS } from '@angular/material-moment-adapter';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    WelcomeComponent,
    AuthenticateComponent,
    NotFoundComponent,
    ServicesPageComponent,
    AboutPageComponent,
  ],
  imports: [
    BrowserModule,
    CoreModule,
    SharedModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    UserModule,
    AdminModule,
    ToastrModule.forRoot({
      timeOut: 1500,
      progressBar: true,
      progressAnimation: 'increasing',
      preventDuplicates: true,
      positionClass: 'toast-top-center',
      closeButton: true,
    }),
    AppRoutingModule,
  ],
  providers: [
    jwtInterceptorProvider,
    { provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: { strict: true } },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

