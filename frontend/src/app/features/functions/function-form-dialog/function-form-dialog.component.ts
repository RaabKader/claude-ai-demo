import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JobFunction } from '../../../core/models/function.model';
import { JobFunctionService } from '../../../core/services/function.service';

@Component({
  selector: 'app-function-form-dialog',
  templateUrl: './function-form-dialog.component.html',
  styleUrls: ['./function-form-dialog.component.scss']
})
export class FunctionFormDialogComponent implements OnInit {
  form!: FormGroup;
  isEdit: boolean;

  constructor(
    private fb: FormBuilder,
    private functionService: JobFunctionService,
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<FunctionFormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: JobFunction | null
  ) {
    this.isEdit = !!data;
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      label:       [this.data?.label       ?? '', [Validators.required, Validators.maxLength(100)]],
      description: [this.data?.description ?? '', Validators.maxLength(500)]
    });
  }

  save(): void {
    if (this.form.invalid) return;
    const op = this.isEdit
      ? this.functionService.update(this.data!.id, this.form.value)
      : this.functionService.create(this.form.value);

    op.subscribe({
      next: () => {
        this.snackBar.open(`Function ${this.isEdit ? 'updated' : 'created'}`, 'Close', { duration: 2000 });
        this.dialogRef.close(true);
      },
      error: () => this.snackBar.open('Operation failed', 'Close', { duration: 3000 })
    });
  }

  cancel(): void {
    this.dialogRef.close(false);
  }
}
