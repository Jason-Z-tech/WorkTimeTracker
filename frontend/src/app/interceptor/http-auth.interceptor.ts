import { HttpInterceptorFn } from '@angular/common/http';

export const httpAuthInterceptor: HttpInterceptorFn = (request, next) => {
  const token = localStorage.getItem('access_token');

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