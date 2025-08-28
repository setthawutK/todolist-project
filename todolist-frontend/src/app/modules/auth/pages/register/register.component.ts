import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '@shared/services/auth/auth.service';
import { AuthSessionService } from '@shared/services/session/auth-session.service';
import { NotificationService } from '@shared/services/notification';
import { isInvalidControl, isValidForm } from '@shared/utils';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [CommonModule, ReactiveFormsModule],
})
export class RegisterComponent {
  private readonly _authService: AuthService = inject(AuthService);
  private readonly _authSession: AuthSessionService = inject(AuthSessionService);
  private readonly _fb: FormBuilder = inject(FormBuilder);
  private readonly _router: Router = inject(Router);
  private readonly _notify: NotificationService = inject(NotificationService);

  registerForm: FormGroup;

  constructor() {
    this.registerForm = this._fb.group({
      name: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    this.registerForm.markAllAsTouched();
    if (isValidForm(this.registerForm)) {
      this._notify.startSpinner();
      const registerBody = { name: this.registerForm.value.name, password: this.registerForm.value.password };
      this._authService.register(registerBody).subscribe({
        next: res => {
          this._notify.stopSpinner();
          this._notify.showSuccess('Register Successful');
          this._router.navigate(['/login']);
          this._authSession.setSession(res);
        },
        error: err => {
          this._notify.stopSpinner();
          this._notify.showError('Register Failed', err?.error?.message ?? 'Please try again');
          console.log(err);
        },
      });
    }
  }

  isInvalidField(controlName: string): boolean {
    return isInvalidControl(this.registerForm.controls[controlName]);
  }

  goToLogin() {
    this._router.navigate(['/login']);
  }
}
