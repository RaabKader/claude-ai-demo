import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Address } from '../../../core/models/address.model';
import { AddressService } from '../../../core/services/address.service';

@Component({
  selector: 'app-address-form-dialog',
  templateUrl: './address-form-dialog.component.html',
  styleUrls: ['./address-form-dialog.component.scss']
})
export class AddressFormDialogComponent implements OnInit {
  form!: FormGroup;
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    private addressService: AddressService,
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<AddressFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Address | null
  ) {
    this.isEdit = !!data;
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      street:  [this.data?.street  ?? '', [Validators.required, Validators.maxLength(200)]],
      city:    [this.data?.city    ?? '', [Validators.required, Validators.maxLength(100)]],
      zipCode: [this.data?.zipCode ?? '', [Validators.required, Validators.maxLength(20)]],
      country: [this.data?.country ?? 'France', [Validators.required, Validators.maxLength(100)]]
    });
  }

  save(): void {
    if (this.form.invalid) return;
    const request = this.form.value;
    const op = this.isEdit
      ? this.addressService.update(this.data!.id, request)
      : this.addressService.create(request);

    op.subscribe({
      next: () => {
        this.snackBar.open(`Address ${this.isEdit ? 'updated' : 'created'}`, 'Close', { duration: 2000 });
        this.dialogRef.close(true);
      },
      error: () => this.snackBar.open('Operation failed', 'Close', { duration: 3000 })
    });
  }

  cancel(): void {
    this.dialogRef.close(false);
  }
}
