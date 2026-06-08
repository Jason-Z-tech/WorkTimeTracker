import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AppUser } from '../../dataaccess/app-user';
import { UserService } from '../../service/user.service';
import { AppAuthService } from '../../service/app-auth.service';
import { AppRoles } from '../../../app.roles';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {

  private userService = inject(UserService);
  private changeDetectorRef = inject(ChangeDetectorRef);

  public authService = inject(AppAuthService);

  users: AppUser[] = [];
  isLoading = false;
  errorMessage = '';

  roles = AppRoles;

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.userService.getAll()
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.changeDetectorRef.detectChanges();
        })
      )
      .subscribe({
        next: (users) => {
          this.users = users;
        },
        error: () => {
          this.errorMessage = 'Die Benutzer konnten nicht geladen werden.';
        }
      });
  }

  deleteUser(id: number): void {
    const confirmed = confirm('Möchtest du diesen Benutzer wirklich löschen?');

    if (!confirmed) {
      return;
    }

    this.userService.delete(id).subscribe({
      next: () => {
        this.loadUsers();
      },
      error: () => {
        this.errorMessage = 'Der Benutzer konnte nicht gelöscht werden.';
      }
    });
  }
}