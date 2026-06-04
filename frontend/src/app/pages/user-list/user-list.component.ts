import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { AppUser } from '../../dataaccess/app-user';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.scss'
})
export class UserListComponent implements OnInit {

  users: AppUser[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.userService.getAll().subscribe({
      next: (users) => {
        this.users = users;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Die Benutzer konnten nicht geladen werden.';
        this.isLoading = false;
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