import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { TimeEntry } from '../../dataaccess/time-entry';
import { TimeEntryRequest } from '../../dataaccess/time-entry-request';
import { Project } from '../../dataaccess/project';
import { AppUser } from '../../dataaccess/app-user';

import { TimeEntryService } from '../../service/time-entry.service';
import { ProjectService } from '../../service/project.service';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-time-entry-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './time-entry-detail.component.html',
  styleUrl: './time-entry-detail.component.scss'
})
export class TimeEntryDetailComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private timeEntryService = inject(TimeEntryService);
  private projectService = inject(ProjectService);
  private userService = inject(UserService);
  private changeDetectorRef = inject(ChangeDetectorRef);

  timeEntryRequest: TimeEntryRequest = {
    startTime: '',
    endTime: '',
    userId: 0,
    projectId: 0
  };

  users: AppUser[] = [];
  projects: Project[] = [];

  isEditMode = false;
  isLoading = false;
  errorMessage = '';

  private timeEntryId = 0;

  ngOnInit(): void {
    this.loadUsers();
    this.loadProjects();

    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.isEditMode = true;
      this.timeEntryId = Number(id);
      this.loadTimeEntry(this.timeEntryId);
    }
  }

  loadUsers(): void {
    this.userService.getAll().subscribe({
      next: (users) => {
        this.users = users;
        this.changeDetectorRef.detectChanges();
      },
      error: () => {
        this.errorMessage = 'Benutzer konnten nicht geladen werden.';
      }
    });
  }

  loadProjects(): void {
    this.projectService.getAll().subscribe({
      next: (projects) => {
        this.projects = projects;
        this.changeDetectorRef.detectChanges();
      },
      error: () => {
        this.errorMessage = 'Projekte konnten nicht geladen werden.';
      }
    });
  }

  loadTimeEntry(id: number): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.timeEntryService.getById(id)
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.changeDetectorRef.detectChanges();
        })
      )
      .subscribe({
        next: (timeEntry: TimeEntry) => {
          this.timeEntryRequest = {
            startTime: this.formatDateTimeForInput(timeEntry.startTime),
            endTime: this.formatDateTimeForInput(timeEntry.endTime),
            userId: timeEntry.user.id,
            projectId: timeEntry.project.id
          };
        },
        error: () => {
          this.errorMessage = 'Der Zeiteintrag konnte nicht geladen werden.';
        }
      });
  }

  saveTimeEntry(): void {
    this.errorMessage = '';

    if (!this.timeEntryRequest.startTime || !this.timeEntryRequest.endTime) {
      this.errorMessage = 'Bitte Startzeit und Endzeit eingeben.';
      return;
    }

    if (this.timeEntryRequest.userId === 0 || this.timeEntryRequest.projectId === 0) {
      this.errorMessage = 'Bitte Benutzer und Projekt auswählen.';
      return;
    }

    if (this.isEditMode) {
      this.timeEntryService.update(this.timeEntryId, this.timeEntryRequest).subscribe({
        next: () => {
          this.router.navigate(['/time-entries']);
        },
        error: () => {
          this.errorMessage = 'Der Zeiteintrag konnte nicht gespeichert werden.';
        }
      });
    } else {
      this.timeEntryService.create(this.timeEntryRequest).subscribe({
        next: () => {
          this.router.navigate(['/time-entries']);
        },
        error: () => {
          this.errorMessage = 'Der Zeiteintrag konnte nicht erstellt werden.';
        }
      });
    }
  }

  private formatDateTimeForInput(value: string): string {
    if (!value) {
      return '';
    }

    return value.substring(0, 16);
  }
}