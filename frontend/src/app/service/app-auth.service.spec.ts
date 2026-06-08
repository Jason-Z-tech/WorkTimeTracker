import { TestBed } from '@angular/core/testing';
import { vi } from 'vitest';

import { AppAuthService } from './app-auth.service';
import { KeycloakService } from './keycloak.service';

describe('AppAuthService', () => {
  let service: AppAuthService;

  const keycloakServiceMock = {
    isLoggedIn: vi.fn(),
    hasRole: vi.fn(),
    hasAnyRole: vi.fn(),
    getRoles: vi.fn(),
    login: vi.fn(),
    logout: vi.fn()
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AppAuthService,
        {
          provide: KeycloakService,
          useValue: keycloakServiceMock
        }
      ]
    });

    service = TestBed.inject(AppAuthService);

    vi.clearAllMocks();
  });

  it('sollte erstellt werden', () => {
    expect(service).toBeTruthy();
  });

  it('sollte prüfen, ob der Benutzer eingeloggt ist', () => {
    keycloakServiceMock.isLoggedIn.mockReturnValue(true);

    expect(service.isLoggedIn()).toBe(true);
    expect(keycloakServiceMock.isLoggedIn).toHaveBeenCalled();
  });

  it('sollte prüfen, ob eine Rolle vorhanden ist', () => {
    keycloakServiceMock.hasRole.mockReturnValue(true);

    expect(service.hasRole('ROLE_admin')).toBe(true);
    expect(keycloakServiceMock.hasRole).toHaveBeenCalledWith('ROLE_admin');
  });

  it('sollte prüfen, ob mindestens eine Rolle vorhanden ist', () => {
    keycloakServiceMock.hasAnyRole.mockReturnValue(true);

    expect(service.hasAnyRole(['ROLE_read', 'ROLE_admin'])).toBe(true);
    expect(keycloakServiceMock.hasAnyRole).toHaveBeenCalledWith(['ROLE_read', 'ROLE_admin']);
  });

  it('sollte alle Rollen zurückgeben', () => {
    keycloakServiceMock.getRoles.mockReturnValue(['ROLE_read', 'ROLE_admin']);

    expect(service.getRoles()).toEqual(['ROLE_read', 'ROLE_admin']);
  });

  it('sollte Login aufrufen', () => {
    service.login();

    expect(keycloakServiceMock.login).toHaveBeenCalled();
  });

  it('sollte Logout aufrufen', () => {
    service.logout();

    expect(keycloakServiceMock.logout).toHaveBeenCalled();
  });
});