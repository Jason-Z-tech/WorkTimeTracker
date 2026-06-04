import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

import { AppAuthService } from '../service/app-auth.service';

@Directive({
  selector: '[appIsInRoles]',
  standalone: true
})
export class IsInRolesDirective {

  constructor(
    private templateRef: TemplateRef<unknown>,
    private viewContainer: ViewContainerRef,
    private authService: AppAuthService
  ) {
  }

  @Input() set appIsInRoles(roles: string[]) {
    this.viewContainer.clear();

    if (this.authService.hasAnyRole(roles)) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }
}