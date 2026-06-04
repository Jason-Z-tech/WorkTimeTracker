import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

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

  project: Project = {
    id: 0,
    name: '',
    description: '',
    active: true
  };

  isEditMode = false;
  isLoading = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService
  ) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.isEditMode = true;
      this.loadProject(Number(id));
    }
  }

  loadProject(id: number): void {
    this.isLoading = true;

    this.projectService.getById(id).subscribe({
      next: (project) => {
        this.project = project;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Das Projekt konnte nicht geladen werden.';
        this.isLoading = false;
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