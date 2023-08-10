import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar'
import {MatIconModule} from '@angular/material/icon';
import {MatSidenavModule} from '@angular/material/sidenav';


const material = [
  MatButtonModule,
  MatToolbarModule,
  MatIconModule,
  MatSidenavModule
];

@NgModule({
  imports: [material],
  exports: [material],
})
export class MaterialModule {}
