import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import EspecialidadeResolve from './route/especialidade-routing-resolve.service';

const especialidadeRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/especialidade.component').then(m => m.EspecialidadeComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/especialidade-detail.component').then(m => m.EspecialidadeDetailComponent),
    resolve: {
      especialidade: EspecialidadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/especialidade-update.component').then(m => m.EspecialidadeUpdateComponent),
    resolve: {
      especialidade: EspecialidadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/especialidade-update.component').then(m => m.EspecialidadeUpdateComponent),
    resolve: {
      especialidade: EspecialidadeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default especialidadeRoute;
