import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Person, PersonRequest } from '../models/person.model';

@Injectable({ providedIn: 'root' })
export class PersonService {
  private readonly url = '/api/v1/persons';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Person[]> {
    return this.http.get<Person[]>(this.url);
  }

  getById(id: number): Observable<Person> {
    return this.http.get<Person>(`${this.url}/${id}`);
  }

  create(request: PersonRequest): Observable<Person> {
    return this.http.post<Person>(this.url, request);
  }

  update(id: number, request: PersonRequest): Observable<Person> {
    return this.http.put<Person>(`${this.url}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
