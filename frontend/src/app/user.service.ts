import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})

export class UserService {
  private apiUrl = 'http://localhost:8080/users/all';

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUserByEmail(email:string): Observable<User> {
      return this.http.get<User>(`${this.apiUrl}/${email}`);
  }
}
