import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatSidenav} from '@angular/material/sidenav';
import { SidenavService } from 'src/app/sidenav.service';
import { UserService } from 'src/app/user/user.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css'],
})
export class SidenavComponent implements AfterViewInit, OnInit {
  @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(
    private userService: UserService,
    private sidenavService: SidenavService
  ) {}

  ngOnInit(): void {
    
  }
  
  ngAfterViewInit(): void {
    this.sidenavService.setSidenav(this.sidenav);
  }

  get isLoggedIn(): boolean {
    return this.userService.isLogged;
  }

  get isAdminIn(): boolean {
    return this.userService.isAdmin;
  }
}
