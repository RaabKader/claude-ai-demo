import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Address } from '../../../core/models/address.model';
import { AddressService } from '../../../core/services/address.service';
import { AddressFormDialogComponent } from '../address-form-dialog/address-form-dialog.component';

@Component({
  selector: 'app-address-list',
  templateUrl: './address-list.component.html',
  styleUrls: ['./address-list.component.scss']
})
export class AddressListComponent implements OnInit {
  displayedColumns = ['street', 'city', 'zipCode', 'country', 'actions'];
  addresses: Address[] = [];
  loading = false;

  constructor(
    private addressService: AddressService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.addressService.getAll().subscribe({
      next: data => { this.addresses = data; this.loading = false; },
      error: () => { this.snackBar.open('Failed to load addresses', 'Close', { duration: 3000 }); this.loading = false; }
    });
  }

  openCreate(): void {
    const ref = this.dialog.open(AddressFormDialogComponent, { width: '500px', data: null });
    ref.afterClosed().subscribe(result => { if (result) this.load(); });
  }

  openEdit(address: Address): void {
    const ref = this.dialog.open(AddressFormDialogComponent, { width: '500px', data: address });
    ref.afterClosed().subscribe(result => { if (result) this.load(); });
  }

  delete(address: Address): void {
    if (!confirm(`Delete address "${address.street}, ${address.city}"?`)) return;
    this.addressService.delete(address.id).subscribe({
      next: () => { this.snackBar.open('Address deleted', 'Close', { duration: 2000 }); this.load(); },
      error: (err) => {
        const msg = err.error?.message || 'Cannot delete address';
        this.snackBar.open(msg, 'Close', { duration: 4000 });
      }
    });
  }
}
