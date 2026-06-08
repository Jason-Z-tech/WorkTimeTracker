import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { TimeEntry } from '../dataaccess/time-entry';
import { TimeEntryRequest } from '../dataaccess/time-entry-request';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TimeEntryService {

  private apiUrl = `${environment.apiUrl}/time-entries`;

  private http = inject(HttpClient);

  getAll(): Observable<TimeEntry[]> {
    return this.http.get<TimeEntry[]>(this.apiUrl);
  }

  getById(id: number): Observable<TimeEntry> {
    return this.http.get<TimeEntry>(`${this.apiUrl}/${id}`);
  }

  getByUserId(userId: number): Observable<TimeEntry[]> {
    return this.http.get<TimeEntry[]>(`${this.apiUrl}/user/${userId}`);
  }

  create(timeEntryRequest: TimeEntryRequest): Observable<TimeEntry> {
    return this.http.post<TimeEntry>(this.apiUrl, timeEntryRequest);
  }

  update(id: number, timeEntryRequest: TimeEntryRequest): Observable<TimeEntry> {
    return this.http.put<TimeEntry>(`${this.apiUrl}/${id}`, timeEntryRequest);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}