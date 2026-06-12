import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'persons', pathMatch: 'full' },
  {
    path: 'persons',
    loadChildren: () => import('./features/persons/persons.module').then(m => m.PersonsModule)
  },
  {
    path: 'addresses',
    loadChildren: () => import('./features/addresses/addresses.module').then(m => m.AddressesModule)
  },
  {
    path: 'functions',
    loadChildren: () => import('./features/functions/functions.module').then(m => m.FunctionsModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
