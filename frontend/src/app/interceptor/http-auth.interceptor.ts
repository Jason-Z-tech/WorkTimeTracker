import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';

import { KeycloakService } from '../service/keycloak.service';

export const httpAuthInterceptor: HttpInterceptorFn = (request, next) => {
  const keycloakService = inject(KeycloakService);
  const token = keycloakService.getToken();

  if (token) {
    const authRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next(authRequest);
  }

  return next(request);
};