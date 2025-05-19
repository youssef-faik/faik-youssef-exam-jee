import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./components/dashboard/dashboard.component').then(c => c.DashboardComponent),
    title: 'Dashboard - Banking System'
  },
  {
    path: 'login',
    loadComponent: () => import('./components/auth/login/login.component').then(c => c.LoginComponent),
    title: 'Login - Banking System'
  },
  {
    path: 'register',
    loadComponent: () => import('./components/auth/register/register.component').then(c => c.RegisterComponent),
    title: 'Register - Banking System'
  },
  {
    path: 'clients',
    loadComponent: () => import('./components/clients/client-list/client-list.component').then(c => c.ClientListComponent),
    title: 'Clients - Banking System'
  },
  {
    path: 'clients/:id',
    loadComponent: () => import('./components/clients/client-detail/client-detail.component').then(c => c.ClientDetailComponent),
    title: 'Client Details - Banking System'
  },
  {
    path: 'credits',
    loadComponent: () => import('./components/credits/credit-list/credit-list.component').then(c => c.CreditListComponent),
    title: 'Credits - Banking System'
  },
  {
    path: 'credits/:id',
    loadComponent: () => import('./components/credits/credit-detail/credit-detail.component').then(c => c.CreditDetailComponent),
    title: 'Credit Details - Banking System'
  },
  {
    path: 'remboursements',
    loadComponent: () => import('./components/remboursements/remboursement-list/remboursement-list.component').then(c => c.RemboursementListComponent),
    title: 'Remboursements - Banking System'
  },
  {
    path: 'remboursements/:id',
    loadComponent: () => import('./components/remboursements/remboursement-detail/remboursement-detail.component').then(c => c.RemboursementDetailComponent),
    title: 'Remboursement Details - Banking System'
  },
  {
    path: '**',
    loadComponent: () => import('./components/not-found/not-found.component').then(c => c.NotFoundComponent),
    title: 'Not Found - Banking System'
  }
];
