import { ApplicationConfig } from '@angular/core';
import { provideRouter, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { UserListComponent } from './user-list/user-list.component';
import { provideHttpClient } from '@angular/common/http';

const routes: Routes = [ // Define routes separately
  { path: 'login', component: LoginComponent },
  { path: 'user-list', component: UserListComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes), // Provide routes here
    provideHttpClient()
  ],
};
