import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import ProntuarioResolve from './route/prontuario-routing-resolve.service';

const prontuarioRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/prontuario.component').then(m => m.ProntuarioComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/prontuario-detail.component').then(m => m.ProntuarioDetailComponent),
    resolve: {
      prontuario: ProntuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/prontuario-update.component').then(m => m.ProntuarioUpdateComponent),
    resolve: {
      prontuario: ProntuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/prontuario-update.component').then(m => m.ProntuarioUpdateComponent),
    resolve: {
      prontuario: ProntuarioResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default prontuarioRoute;
