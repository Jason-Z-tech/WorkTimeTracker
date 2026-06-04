import { Routes } from '@angular/router';
import { appAuthGuard } from './guard/app-auth.guard';
import { AppRoles } from '../app.roles';
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

  {
    path: 'projects',
    component: ProjectListComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Read, AppRoles.Update, AppRoles.Admin] }
  },
  {
    path: 'projects/new',
    component: ProjectDetailComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Admin] }
  },
  {
    path: 'projects/edit/:id',
    component: ProjectDetailComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Admin] }
  },

  {
    path: 'time-entries',
    component: TimeEntryListComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Read, AppRoles.Update, AppRoles.Admin] }
  },
  {
    path: 'time-entries/new',
    component: TimeEntryDetailComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Update, AppRoles.Admin] }
  },
  {
    path: 'time-entries/edit/:id',
    component: TimeEntryDetailComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Update, AppRoles.Admin] }
  },

  {
    path: 'users',
    component: UserListComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Admin] }
  },
  {
    path: 'users/new',
    component: UserDetailComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Admin] }
  },
  {
    path: 'users/edit/:id',
    component: UserDetailComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Admin] }
  },

  {
    path: 'reports',
    component: ReportListComponent,
    canActivate: [appAuthGuard],
    data: { roles: [AppRoles.Read, AppRoles.Update, AppRoles.Admin] }
  },

  { path: 'no-access', component: NoAccessComponent },
  { path: '**', redirectTo: 'dashboard' }
];