import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { ReportService } from './report.service';
import { environment } from '../../environments/environment';
import { TimeEntry } from '../dataaccess/time-entry';

describe('ReportService', () => {
  let service: ReportService;
  let httpMock: HttpTestingController;

  const apiUrl = `${environment.apiUrl}/reports`;

  const mockReportEntry: TimeEntry = {
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

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ReportService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(ReportService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('sollte erstellt werden', () => {
    expect(service).toBeTruthy();
  });

  it('sollte alle Reports laden', () => {
    service.getAll().subscribe(reports => {
      expect(reports.length).toBe(1);
      expect(reports[0].durationMinutes).toBe(240);
    });

    const req = httpMock.expectOne(`${apiUrl}/all`);
    expect(req.request.method).toBe('GET');
    req.flush([mockReportEntry]);
  });

  it('sollte Reports nach Benutzer-ID laden', () => {
    service.getByUserId(1).subscribe(reports => {
      expect(reports.length).toBe(1);
      expect(reports[0].user.username).toBe('admin');
    });

    const req = httpMock.expectOne(`${apiUrl}/user/1`);
    expect(req.request.method).toBe('GET');
    req.flush([mockReportEntry]);
  });
});