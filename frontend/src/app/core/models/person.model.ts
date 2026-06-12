import { Address } from './address.model';
import { JobFunction } from './function.model';

export interface Person {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  address: Address;
  function: JobFunction;
}

export interface PersonRequest {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  addressId: number;
  functionId: number;
}
