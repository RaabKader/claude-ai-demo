import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { PersonListComponent } from './person-list/person-list.component';
import { PersonFormDialogComponent } from './person-form-dialog/person-form-dialog.component';
import { PersonSearchComponent } from './person-search/person-search.component';

@NgModule({
  declarations: [PersonListComponent, PersonFormDialogComponent, PersonSearchComponent],
  imports: [
    SharedModule,
    RouterModule.forChild([
      { path: '', component: PersonListComponent },
      { path: 'search', component: PersonSearchComponent }
    ])
  ]
})
export class PersonsModule {}
