import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Address, AddressRequest } from '../models/address.model';

@Injectable({ providedIn: 'root' })
export class AddressService {
  private readonly url = '/api/v1/addresses';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Address[]> {
    return this.http.get<Address[]>(this.url);
  }

  getById(id: number): Observable<Address> {
    return this.http.get<Address>(`${this.url}/${id}`);
  }

  create(request: AddressRequest): Observable<Address> {
    return this.http.post<Address>(this.url, request);
  }

  update(id: number, request: AddressRequest): Observable<Address> {
    return this.http.put<Address>(`${this.url}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
