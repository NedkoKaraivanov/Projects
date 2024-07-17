import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SidenavService } from 'src/app/sidenav.service';
import { UserService } from 'src/app/user/user.service';
import { WebSocketService } from 'src/app/web-socket.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  constructor(private userService: UserService,
     private router: Router,
      private sidenavService: SidenavService,
       private webSocketService: WebSocketService) {}

  get isLoggedIn(): boolean {
    return this.userService.isLogged;
  }

  logout(): void {
    this.userService.logout().subscribe({
      next: () => {
        this.webSocketService.disconnect();
        this.sidenavService.close(); 
        this.router.navigate(['/']);
      },
      error: () => {
        this.router.navigate(['/login']);
      },
    });
  }
}
