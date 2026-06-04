import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { Project } from '../../dataaccess/project';
import { ProjectService } from '../../service/project.service';
import { AppAuthService } from '../../service/app-auth.service';
import { AppRoles } from '../../../app.roles';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.scss'
})
export class ProjectListComponent implements OnInit {

  projects: Project[] = [];
  isLoading = false;
  errorMessage = '';

  roles = AppRoles;

  constructor(
    private projectService: ProjectService,
    public authService: AppAuthService,
    private changeDetectorRef: ChangeDetectorRef
  ) {
  }

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.projectService.getAll()
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.changeDetectorRef.detectChanges();
        })
      )
      .subscribe({
        next: (projects) => {
          this.projects = projects;
        },
        error: () => {
          this.errorMessage = 'Die Projekte konnten nicht geladen werden.';
        }
      });
  }

  deleteProject(id: number): void {
    const confirmed = confirm('Möchtest du dieses Projekt wirklich löschen?');

    if (!confirmed) {
      return;
    }

    this.projectService.delete(id).subscribe({
      next: () => {
        this.loadProjects();
      },
      error: () => {
        this.errorMessage = 'Das Projekt konnte nicht gelöscht werden.';
      }
    });
  }
}