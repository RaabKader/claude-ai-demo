import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Person } from '../../../core/models/person.model';
import { Address } from '../../../core/models/address.model';
import { JobFunction } from '../../../core/models/function.model';
import { PersonService } from '../../../core/services/person.service';

export interface PersonDialogData {
  person: Person | null;
  addresses: Address[];
  functions: JobFunction[];
}

@Component({
  selector: 'app-person-form-dialog',
  templateUrl: './person-form-dialog.component.html',
  styleUrls: ['./person-form-dialog.component.scss']
})
export class PersonFormDialogComponent implements OnInit {
  form!: FormGroup;
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    private personService: PersonService,
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<PersonFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PersonDialogData
  ) {
    this.isEdit = !!data.person;
  }

  ngOnInit(): void {
    const p = this.data.person;
    this.form = this.fb.group({
      firstName:  [p?.firstName  ?? '', [Validators.required, Validators.maxLength(100)]],
      lastName:   [p?.lastName   ?? '', [Validators.required, Validators.maxLength(100)]],
      email:      [p?.email      ?? '', [Validators.required, Validators.email, Validators.maxLength(255)]],
      phone:      [p?.phone      ?? '', Validators.maxLength(30)],
      addressId:  [p?.address?.id ?? null, Validators.required],
      functionId: [p?.function?.id ?? null, Validators.required]
    });
  }

  save(): void {
    if (this.form.invalid) return;
    const op = this.isEdit
      ? this.personService.update(this.data.person!.id, this.form.value)
      : this.personService.create(this.form.value);

    op.subscribe({
      next: () => {
        this.snackBar.open(`Person ${this.isEdit ? 'updated' : 'created'}`, 'Close', { duration: 2000 });
        this.dialogRef.close(true);
      },
      error: (err) => {
        const msg = err.error?.message || 'Operation failed';
        this.snackBar.open(msg, 'Close', { duration: 4000 });
      }
    });
  }

  cancel(): void {
    this.dialogRef.close(false);
  }
}
