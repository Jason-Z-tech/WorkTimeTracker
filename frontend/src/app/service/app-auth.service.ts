import { inject, Injectable } from '@angular/core';

import { KeycloakService } from './keycloak.service';

@Injectable({
  providedIn: 'root'
})
export class AppAuthService {

  private keycloakService = inject(KeycloakService);

  isLoggedIn(): boolean {
    return this.keycloakService.isLoggedIn();
  }

  getUsername(): string {
    return this.keycloakService.getUsername();
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