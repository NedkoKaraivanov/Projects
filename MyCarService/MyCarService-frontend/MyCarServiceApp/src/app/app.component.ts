import { Component, OnInit } from '@angular/core';
import { UserService } from './user/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'MyCarService';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    const token = localStorage.getItem('access_token');
    if (token) {
      this.userService.getProfile().subscribe({
        next: () => {
        },
        error: () => {
        },
        complete: () => {
        },
      });
    }
  }
}
