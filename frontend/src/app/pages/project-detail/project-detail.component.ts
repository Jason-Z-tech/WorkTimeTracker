import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { Project } from '../../dataaccess/project';
import { ProjectService } from '../../service/project.service';

@Component({
  selector: 'app-project-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './project-detail.component.html',
  styleUrl: './project-detail.component.scss'
})
export class ProjectDetailComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private projectService = inject(ProjectService);
  private changeDetectorRef = inject(ChangeDetectorRef);

  project: Project = {
    id: 0,
    name: '',
    description: '',
    active: true
  };

  isEditMode = false;
  isLoading = false;
  errorMessage = '';

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.isEditMode = true;
      this.loadProject(Number(id));
    }
  }

  loadProject(id: number): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.projectService.getById(id)
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.changeDetectorRef.detectChanges();
        })
      )
      .subscribe({
        next: (project) => {
          this.project = project;
        },
        error: () => {
          this.errorMessage = 'Das Projekt konnte nicht geladen werden.';
        }
      });
  }

  saveProject(): void {
    this.errorMessage = '';

    if (!this.project.name.trim()) {
      this.errorMessage = 'Bitte gib einen Projektnamen ein.';
      return;
    }

    if (this.isEditMode) {
      this.projectService.update(this.project.id, this.project).subscribe({
        next: () => {
          this.router.navigate(['/projects']);
        },
        error: () => {
          this.errorMessage = 'Das Projekt konnte nicht gespeichert werden.';
        }
      });
    } else {
      this.projectService.create(this.project).subscribe({
        next: () => {
          this.router.navigate(['/projects']);
        },
        error: () => {
          this.errorMessage = 'Das Projekt konnte nicht erstellt werden.';
        }
      });
    }
  }
}