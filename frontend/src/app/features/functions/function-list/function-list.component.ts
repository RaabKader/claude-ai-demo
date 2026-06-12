import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { JobFunction } from '../../../core/models/function.model';
import { JobFunctionService } from '../../../core/services/function.service';
import { FunctionFormDialogComponent } from '../function-form-dialog/function-form-dialog.component';

@Component({
  selector: 'app-function-list',
  templateUrl: './function-list.component.html',
  styleUrls: ['./function-list.component.scss']
})
export class FunctionListComponent implements OnInit {
  displayedColumns = ['label', 'description', 'actions'];
  functions: JobFunction[] = [];
  loading = false;

  constructor(
    private functionService: JobFunctionService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.functionService.getAll().subscribe({
      next: data => { this.functions = data; this.loading = false; },
      error: () => { this.snackBar.open('Failed to load functions', 'Close', { duration: 3000 }); this.loading = false; }
    });
  }

  openCreate(): void {
    const ref = this.dialog.open(FunctionFormDialogComponent, { width: '500px', data: null });
    ref.afterClosed().subscribe(result => { if (result) this.load(); });
  }

  openEdit(fn: JobFunction): void {
    const ref = this.dialog.open(FunctionFormDialogComponent, { width: '500px', data: fn });
    ref.afterClosed().subscribe(result => { if (result) this.load(); });
  }

  delete(fn: JobFunction): void {
    if (!confirm(`Delete function "${fn.label}"?`)) return;
    this.functionService.delete(fn.id).subscribe({
      next: () => { this.snackBar.open('Function deleted', 'Close', { duration: 2000 }); this.load(); },
      error: (err) => {
        const msg = err.error?.message || 'Cannot delete function';
        this.snackBar.open(msg, 'Close', { duration: 4000 });
      }
    });
  }
}
