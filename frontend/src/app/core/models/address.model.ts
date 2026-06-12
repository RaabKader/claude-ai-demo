export interface Address {
  id: number;
  street: string;
  city: string;
  zipCode: string;
  country: string;
}

export interface AddressRequest {
  street: string;
  city: string;
  zipCode: string;
  country: string;
}
