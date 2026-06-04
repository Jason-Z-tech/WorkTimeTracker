import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { TimeEntry } from '../dataaccess/time-entry';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private apiUrl = `${environment.apiUrl}/reports`;

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<TimeEntry[]> {
    return this.http.get<TimeEntry[]>(`${this.apiUrl}/all`);
  }

  getByUserId(userId: number): Observable<TimeEntry[]> {
    return this.http.get<TimeEntry[]>(`${this.apiUrl}/user/${userId}`);
  }
}