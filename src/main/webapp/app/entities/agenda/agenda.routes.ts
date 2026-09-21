import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import AgendaResolve from './route/agenda-routing-resolve.service';

const agendaRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/agenda.component').then(m => m.AgendaComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/agenda-detail.component').then(m => m.AgendaDetailComponent),
    resolve: {
      agenda: AgendaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/agenda-update.component').then(m => m.AgendaUpdateComponent),
    resolve: {
      agenda: AgendaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/agenda-update.component').then(m => m.AgendaUpdateComponent),
    resolve: {
      agenda: AgendaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default agendaRoute;
