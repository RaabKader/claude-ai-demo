import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { forkJoin } from 'rxjs';
import { Person } from '../../../core/models/person.model';
import { Address } from '../../../core/models/address.model';
import { JobFunction } from '../../../core/models/function.model';
import { PersonService } from '../../../core/services/person.service';
import { AddressService } from '../../../core/services/address.service';
import { JobFunctionService } from '../../../core/services/function.service';
import { PersonFormDialogComponent } from '../person-form-dialog/person-form-dialog.component';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.scss']
})
export class PersonListComponent implements OnInit {
  displayedColumns = ['firstName', 'lastName', 'email', 'phone', 'address', 'function', 'actions'];
  persons: Person[] = [];
  addresses: Address[] = [];
  functions: JobFunction[] = [];
  loading = false;

  constructor(
    private personService: PersonService,
    private addressService: AddressService,
    private functionService: JobFunctionService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.loading = true;
    forkJoin({
      persons: this.personService.getAll(),
      addresses: this.addressService.getAll(),
      functions: this.functionService.getAll()
    }).subscribe({
      next: ({ persons, addresses, functions }) => {
        this.persons = persons;
        this.addresses = addresses;
        this.functions = functions;
        this.loading = false;
      },
      error: () => { this.snackBar.open('Failed to load data', 'Close', { duration: 3000 }); this.loading = false; }
    });
  }

  openCreate(): void {
    const ref = this.dialog.open(PersonFormDialogComponent, {
      width: '550px',
      data: { person: null, addresses: this.addresses, functions: this.functions }
    });
    ref.afterClosed().subscribe(result => { if (result) this.loadAll(); });
  }

  openEdit(person: Person): void {
    const ref = this.dialog.open(PersonFormDialogComponent, {
      width: '550px',
      data: { person, addresses: this.addresses, functions: this.functions }
    });
    ref.afterClosed().subscribe(result => { if (result) this.loadAll(); });
  }

  delete(person: Person): void {
    if (!confirm(`Delete "${person.firstName} ${person.lastName}"?`)) return;
    this.personService.delete(person.id).subscribe({
      next: () => { this.snackBar.open('Person deleted', 'Close', { duration: 2000 }); this.loadAll(); },
      error: () => this.snackBar.open('Delete failed', 'Close', { duration: 3000 })
    });
  }
}
