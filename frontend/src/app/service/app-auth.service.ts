import { Injectable } from '@angular/core';

import { KeycloakService } from './keycloak.service';

@Injectable({
  providedIn: 'root'
})
export class AppAuthService {

  constructor(private keycloakService: KeycloakService) {
  }

  isLoggedIn(): boolean {
    return this.keycloakService.isLoggedIn();
  }

  hasRole(role: string): boolean {
    return this.keycloakService.hasRole(role);
  }

  hasAnyRole(roles: string[]): boolean {
    return this.keycloakService.hasAnyRole(roles);
  }

  getRoles(): string[] {
    return this.keycloakService.getRoles();
  }

  login(): void {
    this.keycloakService.login();
  }

  logout(): void {
    this.keycloakService.logout();
  }
}