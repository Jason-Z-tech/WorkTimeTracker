import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Report } from '../../dataaccess/report';
import { ReportService } from '../../service/report.service';

@Component({
  selector: 'app-report-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './report-list.component.html',
  styleUrl: './report-list.component.scss'
})
export class ReportListComponent implements OnInit {

  reports: Report[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private reportService: ReportService) {
  }

  ngOnInit(): void {
    this.loadReports();
  }

  loadReports(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.reportService.getAll().subscribe({
      next: (reports) => {
        this.reports = reports;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Die Reports konnten nicht geladen werden.';
        this.isLoading = false;
      }
    });
  }

  getHours(totalMinutes: number): string {
    const hours = totalMinutes / 60;
    return hours.toFixed(2);
  }
}