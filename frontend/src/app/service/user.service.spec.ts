import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { UserService } from './user.service';
import { environment } from '../../environments/environment';
import { AppUser } from '../dataaccess/app-user';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  const apiUrl = `${environment.apiUrl}/users`;

  const mockUser: AppUser = {
    id: 1,
    username: 'admin',
    email: 'admin@admin.ch'
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        UserService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('sollte erstellt werden', () => {
    expect(service).toBeTruthy();
  });

  it('sollte alle Benutzer laden', () => {
    service.getAll().subscribe(users => {
      expect(users.length).toBe(1);
      expect(users[0].username).toBe('admin');
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush([mockUser]);
  });

  it('sollte einen Benutzer nach ID laden', () => {
    service.getById(1).subscribe(user => {
      expect(user.id).toBe(1);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('sollte einen Benutzer erstellen', () => {
    service.create(mockUser).subscribe(user => {
      expect(user.email).toBe('admin@admin.ch');
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    req.flush(mockUser);
  });

  it('sollte einen Benutzer aktualisieren', () => {
    service.update(1, mockUser).subscribe(user => {
      expect(user.username).toBe('admin');
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('PUT');
    req.flush(mockUser);
  });

  it('sollte einen Benutzer löschen', () => {
    service.delete(1).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});