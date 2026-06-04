import { APP_INITIALIZER, ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { httpAuthInterceptor } from './interceptor/http-auth.interceptor';
import { KeycloakService } from './service/keycloak.service';

function initializeKeycloak(keycloakService: KeycloakService): () => Promise<boolean> {
  return () => keycloakService.init();
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(
      withInterceptors([httpAuthInterceptor])
    ),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ]
};