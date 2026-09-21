import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ProfissionalResolve from './route/profissional-routing-resolve.service';

const profissionalRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/profissional.component').then(m => m.ProfissionalComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/profissional-detail.component').then(m => m.ProfissionalDetailComponent),
    resolve: {
      profissional: ProfissionalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/profissional-update.component').then(m => m.ProfissionalUpdateComponent),
    resolve: {
      profissional: ProfissionalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/profissional-update.component').then(m => m.ProfissionalUpdateComponent),
    resolve: {
      profissional: ProfissionalResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default profissionalRoute;
