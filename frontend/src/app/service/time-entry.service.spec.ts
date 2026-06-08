import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { TimeEntryService } from './time-entry.service';
import { environment } from '../../environments/environment';
import { TimeEntry } from '../dataaccess/time-entry';
import { TimeEntryRequest } from '../dataaccess/time-entry-request';

describe('TimeEntryService', () => {
  let service: TimeEntryService;
  let httpMock: HttpTestingController;

  const apiUrl = `${environment.apiUrl}/time-entries`;

  const mockTimeEntry: TimeEntry = {
    id: 1,
    startTime: '2026-06-04T08:00',
    endTime: '2026-06-04T12:00',
    durationMinutes: 240,
    user: {
      id: 1,
      username: 'admin',
      email: 'admin@admin.ch'
    },
    project: {
      id: 1,
      name: 'Testprojekt',
      description: 'Beschreibung',
      active: true
    }
  };

  const mockRequest: TimeEntryRequest = {
    startTime: '2026-06-04T08:00',
    endTime: '2026-06-04T12:00',
    userId: 1,
    projectId: 1
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        TimeEntryService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(TimeEntryService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('sollte erstellt werden', () => {
    expect(service).toBeTruthy();
  });

  it('sollte alle Zeiteinträge laden', () => {
    service.getAll().subscribe(entries => {
      expect(entries.length).toBe(1);
      expect(entries[0].durationMinutes).toBe(240);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush([mockTimeEntry]);
  });

  it('sollte einen Zeiteintrag nach ID laden', () => {
    service.getById(1).subscribe(entry => {
      expect(entry.id).toBe(1);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTimeEntry);
  });

  it('sollte Zeiteinträge eines Benutzers laden', () => {
    service.getByUserId(1).subscribe(entries => {
      expect(entries.length).toBe(1);
      expect(entries[0].user.username).toBe('admin');
    });

    const req = httpMock.expectOne(`${apiUrl}/user/1`);
    expect(req.request.method).toBe('GET');
    req.flush([mockTimeEntry]);
  });

  it('sollte einen Zeiteintrag erstellen', () => {
    service.create(mockRequest).subscribe(entry => {
      expect(entry.project.name).toBe('Testprojekt');
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockRequest);
    req.flush(mockTimeEntry);
  });

  it('sollte einen Zeiteintrag aktualisieren', () => {
    service.update(1, mockRequest).subscribe(entry => {
      expect(entry.id).toBe(1);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockRequest);
    req.flush(mockTimeEntry);
  });

  it('sollte einen Zeiteintrag löschen', () => {
    service.delete(1).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});