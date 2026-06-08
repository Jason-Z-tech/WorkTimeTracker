import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';

import { ProjectService } from './project.service';
import { environment } from '../../environments/environment';
import { Project } from '../dataaccess/project';

describe('ProjectService', () => {
  let service: ProjectService;
  let httpMock: HttpTestingController;

  const apiUrl = `${environment.apiUrl}/projects`;

  const mockProject: Project = {
    id: 1,
    name: 'Testprojekt',
    description: 'Beschreibung',
    active: true
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ProjectService,
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    });

    service = TestBed.inject(ProjectService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('sollte erstellt werden', () => {
    expect(service).toBeTruthy();
  });

  it('sollte alle Projekte laden', () => {
    service.getAll().subscribe(projects => {
      expect(projects.length).toBe(1);
      expect(projects[0].name).toBe('Testprojekt');
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush([mockProject]);
  });

  it('sollte ein Projekt nach ID laden', () => {
    service.getById(1).subscribe(project => {
      expect(project.id).toBe(1);
      expect(project.name).toBe('Testprojekt');
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockProject);
  });

  it('sollte ein Projekt erstellen', () => {
    service.create(mockProject).subscribe(project => {
      expect(project.name).toBe('Testprojekt');
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockProject);
    req.flush(mockProject);
  });

  it('sollte ein Projekt aktualisieren', () => {
    service.update(1, mockProject).subscribe(project => {
      expect(project.id).toBe(1);
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockProject);
    req.flush(mockProject);
  });

  it('sollte ein Projekt löschen', () => {
    service.delete(1).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${apiUrl}/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});