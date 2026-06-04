import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs';

import { TimeEntry } from '../../dataaccess/time-entry';
import { ReportService } from '../../service/report.service';

@Component({
  selector: 'app-report-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './report-list.component.html',
  styleUrl: './report-list.component.scss'
})
export class ReportListComponent implements OnInit {

  reports: TimeEntry[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(
    private reportService: ReportService,
    private changeDetectorRef: ChangeDetectorRef
  ) {
  }

  ngOnInit(): void {
    this.loadReports();
  }

  loadReports(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.reportService.getAll()
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.changeDetectorRef.detectChanges();
        })
      )
      .subscribe({
        next: (reports) => {
          this.reports = reports;
        },
        error: () => {
          this.errorMessage = 'Die Reports konnten nicht geladen werden.';
        }
      });
  }

  getHours(durationMinutes: number): string {
    const hours = durationMinutes / 60;
    return hours.toFixed(2);
  }
}