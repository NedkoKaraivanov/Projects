import { Component } from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css'],
})
export class WelcomeComponent {
  image1 = 'assets/images/diagnostics2.jpg';
  image2 = 'assets/images/oil_change.png';
  image3 = 'assets/images/suspension.jpeg';
  image4 = 'assets/images/tire_change.jpeg';
}
