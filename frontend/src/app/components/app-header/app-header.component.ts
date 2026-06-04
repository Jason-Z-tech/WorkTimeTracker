import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

import { AppAuthService } from '../../service/app-auth.service';
import { AppRoles } from '../../../app.roles';
import { AppLoginComponent } from '../app-login/app-login.component';

@Component({
  selector: 'app-app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, AppLoginComponent],
  templateUrl: './app-header.component.html',
  styleUrl: './app-header.component.scss'
})
export class AppHeaderComponent {

  roles = AppRoles;

  constructor(public authService: AppAuthService) {
  }
}