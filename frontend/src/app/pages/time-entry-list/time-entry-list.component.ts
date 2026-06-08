import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { TimeEntry } from '../../dataaccess/time-entry';
import { TimeEntryService } from '../../service/time-entry.service';
import { AppAuthService } from '../../service/app-auth.service';
import { AppRoles } from '../../../app.roles';

@Component({
  selector: 'app-time-entry-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './time-entry-list.component.html',
  styleUrl: './time-entry-list.component.scss'
})
export class TimeEntryListComponent implements OnInit {

  private timeEntryService = inject(TimeEntryService);
  private changeDetectorRef = inject(ChangeDetectorRef);

  public authService = inject(AppAuthService);

  timeEntries: TimeEntry[] = [];
  isLoading = false;
  errorMessage = '';

  roles = AppRoles;

  ngOnInit(): void {
    this.loadTimeEntries();
  }

  loadTimeEntries(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.timeEntryService.getAll()
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.changeDetectorRef.detectChanges();
        })
      )
      .subscribe({
        next: (timeEntries) => {
          this.timeEntries = timeEntries;
        },
        error: () => {
          this.errorMessage = 'Die Zeiteinträge konnten nicht geladen werden.';
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