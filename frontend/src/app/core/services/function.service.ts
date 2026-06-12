import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobFunction, JobFunctionRequest } from '../models/function.model';

@Injectable({ providedIn: 'root' })
export class JobFunctionService {
  private readonly url = '/api/v1/functions';

  constructor(private http: HttpClient) {}

  getAll(): Observable<JobFunction[]> {
    return this.http.get<JobFunction[]>(this.url);
  }

  getById(id: number): Observable<JobFunction> {
    return this.http.get<JobFunction>(`${this.url}/${id}`);
  }

  create(request: JobFunctionRequest): Observable<JobFunction> {
    return this.http.post<JobFunction>(this.url, request);
  }

  update(id: number, request: JobFunctionRequest): Observable<JobFunction> {
    return this.http.put<JobFunction>(`${this.url}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
