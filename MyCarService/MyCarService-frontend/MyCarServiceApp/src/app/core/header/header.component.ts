import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/user/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  constructor(private userService: UserService, private router: Router) {}

  get isLoggedIn(): boolean {
    return this.userService.isLogged;
  }

  logout(): void {
    this.userService.logout().subscribe({
      next: () => {
        localStorage.setItem('access_token', '');
        localStorage.setItem('refresh_token', '');
        localStorage.setItem('roles', '');
        this.router.navigate(['/']);
      },
      error: () => {
        this.router.navigate(['/login']);
      },
    });
  }

}
