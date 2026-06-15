import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  navLinks = [
    { path: '/persons', label: 'Persons', icon: 'people' },
    { path: '/persons/search', label: 'Search Persons', icon: 'search' },
    { path: '/addresses', label: 'Addresses', icon: 'location_on' },
    { path: '/functions', label: 'Functions', icon: 'work' }
  ];
}
