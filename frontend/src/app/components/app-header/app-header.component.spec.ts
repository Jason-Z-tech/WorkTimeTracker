import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { vi } from 'vitest';

import { AppHeaderComponent } from './app-header.component';
import { AppAuthService } from '../../service/app-auth.service';

describe('AppHeaderComponent', () => {
  let component: AppHeaderComponent;
  let fixture: ComponentFixture<AppHeaderComponent>;

  const authServiceMock = {
    isLoggedIn: vi.fn().mockReturnValue(true),
    getUsername: vi.fn().mockReturnValue('admin'),
    hasRole: vi.fn().mockReturnValue(true),
    hasAnyRole: vi.fn().mockReturnValue(true),
    login: vi.fn(),
    logout: vi.fn(),
    getRoles: vi.fn().mockReturnValue(['ROLE_read', 'ROLE_update', 'ROLE_admin'])
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppHeaderComponent],
      providers: [
        provideRouter([]),
        {
          provide: AppAuthService,
          useValue: authServiceMock
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});