import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user/user.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css'],
})
export class SidenavComponent {
  constructor(private userService: UserService) {}

  get isLoggedIn(): boolean {
    return this.userService.isLogged;
  }

}
