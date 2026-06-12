import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { AddressListComponent } from './address-list/address-list.component';
import { AddressFormDialogComponent } from './address-form-dialog/address-form-dialog.component';

@NgModule({
  declarations: [AddressListComponent, AddressFormDialogComponent],
  imports: [
    SharedModule,
    RouterModule.forChild([{ path: '', component: AddressListComponent }])
  ]
})
export class AddressesModule {}
