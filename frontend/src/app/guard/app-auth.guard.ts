import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

import { AppAuthService } from '../service/app-auth.service';

export const appAuthGuard: CanActivateFn = (route) => {
  const authService = inject(AppAuthService);
  const router = inject(Router);

  const roles = route.data['roles'] as string[] | undefined;

  if (!authService.isLoggedIn()) {
    router.navigate(['/no-access']);
    return false;
  }

  if (roles && !authService.hasAnyRole(roles)) {
    router.navigate(['/no-access']);
    return false;
  }

  return true;
};