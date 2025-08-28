import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '@shared/services/auth/auth.service';
import { NotificationService } from '@shared/services/notification';
import { AuthSessionService } from '@shared/services/session/auth-session.service';
import { isInvalidControl, isValidForm } from '@shared/utils';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  private readonly _authService: AuthService = inject(AuthService);
  private readonly _authSession: AuthSessionService = inject(AuthSessionService);
  private readonly _fb: FormBuilder = inject(FormBuilder);
  private readonly _router: Router = inject(Router);
  private readonly _notify: NotificationService = inject(NotificationService);

  loginForm: FormGroup;

  constructor() {
    this.loginForm = this._fb.group({
      name: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    this.loginForm.markAllAsTouched();
    if (isValidForm(this.loginForm)) {
      this._notify.startSpinner();
      const loginData = this.loginForm.value;
      this._authService.login(loginData).subscribe({
        next: res => {
          this._notify.stopSpinner();
          this._notify.showSuccess('Login Successful');
          this._router.navigate(['/home']);
          this._authSession.setSession(res);
        },
        error: err => {
          this._notify.stopSpinner();
          this._notify.showError('Login Failed', err?.error?.message ?? 'Please try again');
          console.log(err);
        },
      });
    }
  }

  isInvalidField(controlName: string): boolean {
    return isInvalidControl(this.loginForm.controls[controlName]);
  }

  goToRegister(): void {
    this._router.navigate(['/register']);
  }
}
