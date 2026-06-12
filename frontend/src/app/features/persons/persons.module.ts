import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { PersonListComponent } from './person-list/person-list.component';
import { PersonFormDialogComponent } from './person-form-dialog/person-form-dialog.component';

@NgModule({
  declarations: [PersonListComponent, PersonFormDialogComponent],
  imports: [
    SharedModule,
    RouterModule.forChild([{ path: '', component: PersonListComponent }])
  ]
})
export class PersonsModule {}
