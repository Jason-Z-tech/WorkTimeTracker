import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AppUser } from '../../dataaccess/app-user';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-user-detail',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './user-detail.component.html',
  styleUrl: './user-detail.component.scss'
})
export class UserDetailComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private userService = inject(UserService);
  private changeDetectorRef = inject(ChangeDetectorRef);

  user: AppUser = {
    id: 0,
    username: '',
    email: ''
  };

  isEditMode = false;
  isLoading = false;
  errorMessage = '';

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.isEditMode = true;
      this.loadUser(Number(id));
    }
  }

  loadUser(id: number): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.userService.getById(id)
      .pipe(
        finalize(() => {
          this.isLoading = false;
          this.changeDetectorRef.detectChanges();
        })
      )
      .subscribe({
        next: (user) => {
          this.user = user;
        },
        error: () => {
          this.errorMessage = 'Der Benutzer konnte nicht geladen werden.';
        }
      });
  }

  saveUser(): void {
    this.errorMessage = '';

    if (!this.user.username.trim()) {
      this.errorMessage = 'Bitte gib einen Benutzernamen ein.';
      return;
    }

    if (!this.user.email.trim()) {
      this.errorMessage = 'Bitte gib eine E-Mail-Adresse ein.';
      return;
    }

    if (this.isEditMode) {
      this.userService.update(this.user.id, this.user).subscribe({
        next: () => {
          this.router.navigate(['/users']);
        },
        error: () => {
          this.errorMessage = 'Der Benutzer konnte nicht gespeichert werden.';
        }
      });
    } else {
      this.userService.create(this.user).subscribe({
        next: () => {
          this.router.navigate(['/users']);
        },
        error: () => {
          this.errorMessage = 'Der Benutzer konnte nicht erstellt werden.';
        }
      });
    }
  }
}