import { Directive, inject, Input, TemplateRef, ViewContainerRef } from '@angular/core';

import { AppAuthService } from '../service/app-auth.service';

@Directive({
  selector: '[appIsInRole]',
  standalone: true
})
export class IsInRoleDirective {

  private templateRef = inject(TemplateRef<unknown>);
  private viewContainer = inject(ViewContainerRef);
  private authService = inject(AppAuthService);

  @Input() set appIsInRole(role: string) {
    this.viewContainer.clear();

    if (this.authService.hasRole(role)) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }
}