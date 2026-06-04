import { Routes } from '@angular/router';

import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { NoAccessComponent } from './pages/no-access/no-access.component';
import { ProjectListComponent } from './pages/project-list/project-list.component';
import { ProjectDetailComponent } from './pages/project-detail/project-detail.component';
import { TimeEntryListComponent } from './pages/time-entry-list/time-entry-list.component';
import { TimeEntryDetailComponent } from './pages/time-entry-detail/time-entry-detail.component';
import { UserListComponent } from './pages/user-list/user-list.component';
import { UserDetailComponent } from './pages/user-detail/user-detail.component';
import { ReportListComponent } from './pages/report-list/report-list.component';

export const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'dashboard', component: DashboardComponent },

  { path: 'projects', component: ProjectListComponent },
  { path: 'projects/new', component: ProjectDetailComponent },
  { path: 'projects/edit/:id', component: ProjectDetailComponent },

  { path: 'time-entries', component: TimeEntryListComponent },
  { path: 'time-entries/new', component: TimeEntryDetailComponent },
  { path: 'time-entries/edit/:id', component: TimeEntryDetailComponent },

  { path: 'users', component: UserListComponent },
  { path: 'users/new', component: UserDetailComponent },
  { path: 'users/edit/:id', component: UserDetailComponent },

  { path: 'reports', component: ReportListComponent },

  { path: 'no-access', component: NoAccessComponent },
  { path: '**', redirectTo: 'dashboard' }
];