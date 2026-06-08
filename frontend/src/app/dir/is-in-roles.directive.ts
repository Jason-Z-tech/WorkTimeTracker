import { Directive, inject, Input, TemplateRef, ViewContainerRef } from '@angular/core';

import { AppAuthService } from '../service/app-auth.service';

@Directive({
  selector: '[appIsInRoles]',
  standalone: true
})
export class IsInRolesDirective {

  private templateRef = inject(TemplateRef<unknown>);
  private viewContainer = inject(ViewContainerRef);
  private authService = inject(AppAuthService);

  @Input() set appIsInRoles(roles: string[]) {
    this.viewContainer.clear();

    if (this.authService.hasAnyRole(roles)) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }
}