import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Person, PersonRequest, PersonSearchCriteria } from '../models/person.model';
import { Page, PageRequest } from '../models/page.model';

@Injectable({ providedIn: 'root' })
export class PersonService {
  private readonly url = '/api/v1/persons';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Person[]> {
    return this.http.get<Person[]>(this.url);
  }

  search(criteria: PersonSearchCriteria, pageRequest: PageRequest): Observable<Page<Person>> {
    let params = new HttpParams()
      .set('page', pageRequest.page)
      .set('size', pageRequest.size);
    if (pageRequest.sort) {
      params = params.set('sort', pageRequest.sort);
    }
    return this.http.post<Page<Person>>(`${this.url}/search`, criteria, { params });
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
