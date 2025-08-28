import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  private readonly _httpClient: HttpClient = inject(HttpClient);

  constructor() {}

  getTodos(): Observable<any> {
    return this._httpClient.get<any>(todoListAPI.authLoginFinishedShowlist);
  }

  addTodo(body: any): Observable<any> {
    return this._httpClient.post<any>(todoListAPI.createlist, body);
  }

  updateTodo(body: any): Observable<any> {
    return this._httpClient.patch<any>(todoListAPI.updateUp, body);
  }

  deleteTodo(id: string): Observable<any> {
    return this._httpClient.delete<any>(todoListAPI.delete, {
      headers: {
        orderID: id,
      },
    });
  }

  // logout(body: LogoutReq): Observable<SuccessResponseVoid> {
  //   return this._httpClient.post<SuccessResponseVoid>(ileaveAPI.apiV1AuthenticationsLogout, body);
  // }
}
