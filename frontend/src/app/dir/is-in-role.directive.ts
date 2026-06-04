import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

import { AppAuthService } from '../service/app-auth.service';

@Directive({
  selector: '[appIsInRole]',
  standalone: true
})
export class IsInRoleDirective {

  constructor(
    private templateRef: TemplateRef<unknown>,
    private viewContainer: ViewContainerRef,
    private authService: AppAuthService
  ) {
  }

  @Input() set appIsInRole(role: string) {
    this.viewContainer.clear();

    if (this.authService.hasRole(role)) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }
}