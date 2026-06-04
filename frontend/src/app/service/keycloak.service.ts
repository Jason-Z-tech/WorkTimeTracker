import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';

@Injectable({
  providedIn: 'root'
})
export class KeycloakService {

  private keycloak = new Keycloak({
    url: 'http://localhost:8080',
    realm: 'WorkTimeApp',
    clientId: 'WorkTime'
  });

    async init(): Promise<boolean> {
    const authenticated = await this.keycloak.init({
        onLoad: 'check-sso',
        checkLoginIframe: false,
        pkceMethod: 'S256'
    });

    if (authenticated && this.keycloak.token) {
        localStorage.setItem('access_token', this.keycloak.token);
    }

    return authenticated;
    }

  login(): void {
    this.keycloak.login({
      redirectUri: 'http://localhost:4200'
    });
  }

  logout(): void {
    localStorage.removeItem('access_token');

    this.keycloak.logout({
      redirectUri: 'http://localhost:4200'
    });
  }

  isLoggedIn(): boolean {
    return !!this.keycloak.authenticated;
  }

  getToken(): string | undefined {
    return this.keycloak.token;
  }

  getRoles(): string[] {
    const tokenParsed = this.keycloak.tokenParsed as any;
    return tokenParsed?.resource_access?.['WorkTime']?.roles ?? [];
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

  hasAnyRole(roles: string[]): boolean {
    return roles.some(role => this.hasRole(role));
  }
}