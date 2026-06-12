import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { FunctionListComponent } from './function-list/function-list.component';
import { FunctionFormDialogComponent } from './function-form-dialog/function-form-dialog.component';

@NgModule({
  declarations: [FunctionListComponent, FunctionFormDialogComponent],
  imports: [
    SharedModule,
    RouterModule.forChild([{ path: '', component: FunctionListComponent }])
  ]
})
export class FunctionsModule {}
