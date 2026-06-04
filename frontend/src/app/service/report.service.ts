import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Report } from '../dataaccess/report';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private apiUrl = `${environment.apiUrl}/reports`;

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Report[]> {
    return this.http.get<Report[]>(this.apiUrl);
  }

  getByUserId(userId: number): Observable<Report[]> {
    return this.http.get<Report[]>(`${this.apiUrl}/user/${userId}`);
  }
}