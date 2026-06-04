import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { TimeEntry } from '../../dataaccess/time-entry';
import { TimeEntryService } from '../../service/time-entry.service';

@Component({
  selector: 'app-time-entry-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './time-entry-list.component.html',
  styleUrl: './time-entry-list.component.scss'
})
export class TimeEntryListComponent implements OnInit {

  timeEntries: TimeEntry[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private timeEntryService: TimeEntryService) {
  }

  ngOnInit(): void {
    this.loadTimeEntries();
  }

  loadTimeEntries(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.timeEntryService.getAll().subscribe({
      next: (timeEntries) => {
        this.timeEntries = timeEntries;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Die Zeiteinträge konnten nicht geladen werden.';
        this.isLoading = false;
      }
    });
  }

  deleteTimeEntry(id: number): void {
    const confirmed = confirm('Möchtest du diesen Zeiteintrag wirklich löschen?');

    if (!confirmed) {
      return;
    }

    this.timeEntryService.delete(id).subscribe({
      next: () => {
        this.loadTimeEntries();
      },
      error: () => {
        this.errorMessage = 'Der Zeiteintrag konnte nicht gelöscht werden.';
      }
    });
  }
}