import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import SalaResolve from './route/sala-routing-resolve.service';

const salaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/sala.component').then(m => m.SalaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/sala-detail.component').then(m => m.SalaDetailComponent),
    resolve: {
      sala: SalaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/sala-update.component').then(m => m.SalaUpdateComponent),
    resolve: {
      sala: SalaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/sala-update.component').then(m => m.SalaUpdateComponent),
    resolve: {
      sala: SalaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default salaRoute;
