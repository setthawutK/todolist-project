import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';
const BASE = environment.apiBase;
@Injectable({
  providedIn: 'root',
})
export class TodoService {
  private readonly _httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  getTodos(): Observable<any> {
    return this._httpClient.get<any>(`${BASE}/auth/loginFinished/showlist`);
  }

  addTodo(body: any): Observable<any> {
    return this._httpClient.post<any>(`${BASE}/create-list`, body);
  }

  updateTodo(body: any): Observable<any> {
    return this._httpClient.patch<any>(`${BASE}/updateUp`, body);
  }

  deleteTodo(id: string): Observable<any> {
    return this._httpClient.delete<any>(`${BASE}/delete`, {
      headers: {
        orderID: id,
      },
    });
  }

  // logout(body: LogoutReq): Observable<SuccessResponseVoid> {
  //   return this._httpClient.post<SuccessResponseVoid>(ileaveAPI.apiV1AuthenticationsLogout, body);
  // }
}
