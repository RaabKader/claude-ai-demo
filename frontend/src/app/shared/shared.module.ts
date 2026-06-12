import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatTooltipModule } from '@angular/material/tooltip';

const MATERIAL_MODULES = [
  MatTableModule, MatButtonModule, MatIconModule, MatDialogModule,
  MatFormFieldModule, MatInputModule, MatSelectModule, MatSnackBarModule,
  MatToolbarModule, MatSidenavModule, MatListModule, MatProgressSpinnerModule,
  MatCardModule, MatTooltipModule
];

@NgModule({
  exports: [CommonModule, ReactiveFormsModule, ...MATERIAL_MODULES]
})
export class SharedModule {}
