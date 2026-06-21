import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Person, PersonSearchCriteria } from '../../../core/models/person.model';
import { PersonService } from '../../../core/services/person.service';

@Component({
  selector: 'app-person-search',
  templateUrl: './person-search.component.html',
  styleUrls: ['./person-search.component.scss']
})
export class PersonSearchComponent implements OnInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  displayedColumns = ['firstName', 'lastName', 'email', 'phone', 'city', 'country', 'function'];
  pageSizeOptions = [5, 10, 25, 50];

  form!: FormGroup;
  persons: Person[] = [];
  totalElements = 0;
  pageIndex = 0;
  pageSize = 10;
  sortField = 'lastName';
  sortDir: 'asc' | 'desc' = 'asc';
  loading = false;
  searched = false;

  constructor(
    private fb: FormBuilder,
    private personService: PersonService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      firstName: [''],
      lastName: [''],
      email: [''],
      phone: [''],
      street: [''],
      city: [''],
      zipCode: [''],
      country: [''],
      functionLabel: ['']
    });
    this.load();
  }

  /** Triggered by the Search button — always restart from the first page. */
  onSearch(): void {
    this.pageIndex = 0;
    if (this.paginator) {
      this.paginator.firstPage();
    }
    this.load();
  }

  onClear(): void {
    this.form.reset({
      firstName: '', lastName: '', email: '', phone: '',
      street: '', city: '', zipCode: '', country: '', functionLabel: ''
    });
    this.onSearch();
  }

  onPage(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.load();
  }

  onSortChange(sort: Sort): void {
    this.sortField = sort.direction ? sort.active : 'lastName';
    this.sortDir = sort.direction || 'asc';
    this.pageIndex = 0;
    if (this.paginator) {
      this.paginator.firstPage();
    }
    this.load();
  }

  private load(): void {
    this.loading = true;
    const criteria = this.buildCriteria();
    this.personService
      .search(criteria, {
        page: this.pageIndex,
        size: this.pageSize,
        sort: `${this.sortField},${this.sortDir}`
      })
      .subscribe({
        next: (result) => {
          this.persons = result.content;
          this.totalElements = result.totalElements;
          this.loading = false;
          this.searched = true;
        },
        error: () => {
          this.snackBar.open('Search failed', 'Close', { duration: 3000 });
          this.loading = false;
        }
      });
  }

  /** Drops blank fields so the backend treats them as "no filter". */
  private buildCriteria(): PersonSearchCriteria {
    const raw = this.form.value as Record<string, string>;
    const criteria: PersonSearchCriteria = {};
    Object.keys(raw).forEach((key) => {
      const value = (raw[key] ?? '').trim();
      if (value) {
        (criteria as Record<string, string>)[key] = value;
      }
    });
    return criteria;
  }
}
