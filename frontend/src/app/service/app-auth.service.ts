import { Injectable } from '@angular/core';
import { AppRoles } from '../../app.roles';

@Injectable({
  providedIn: 'root'
})
export class AppAuthService {

  private roles: string[] = [
    AppRoles.Read,
    AppRoles.Update,
    AppRoles.Admin
  ];

  isLoggedIn(): boolean {
    return true;
  }

  hasRole(role: string): boolean {
    return this.roles.includes(role);
  }

  hasAnyRole(roles: string[]): boolean {
    return roles.some(role => this.hasRole(role));
  }

  getRoles(): string[] {
    return this.roles;
  }
}