import { Component } from '@angular/core';

import { AppAuthService } from '../../service/app-auth.service';

@Component({
  selector: 'app-app-login',
  standalone: true,
  imports: [],
  templateUrl: './app-login.component.html',
  styleUrl: './app-login.component.scss'
})
export class AppLoginComponent {

  constructor(public authService: AppAuthService) {
  }

  login(): void {
    this.authService.login();
  }

  logout(): void {
    this.authService.logout();
  }
}