import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
const BASE = environment.apiBase;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly _httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  login(body: any): Observable<any> {
    return this._httpClient.post<any>(`${BASE}/auth/login`, body);
  }

  register(body: any): Observable<any> {
    return this._httpClient.post<any>(`${BASE}/RegisterNaja`, body);
  }

  // logout(body: LogoutReq): Observable<SuccessResponseVoid> {
  //   return this._httpClient.post<SuccessResponseVoid>(ileaveAPI.apiV1AuthenticationsLogout, body);
  // }
}
