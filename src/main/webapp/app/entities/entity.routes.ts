import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'clinicaMultiTerapiasApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'agenda',
    data: { pageTitle: 'clinicaMultiTerapiasApp.agenda.home.title' },
    loadChildren: () => import('./agenda/agenda.routes'),
  },
  {
    path: 'especialidade',
    data: { pageTitle: 'clinicaMultiTerapiasApp.especialidade.home.title' },
    loadChildren: () => import('./especialidade/especialidade.routes'),
  },
  {
    path: 'paciente',
    data: { pageTitle: 'clinicaMultiTerapiasApp.paciente.home.title' },
    loadChildren: () => import('./paciente/paciente.routes'),
  },
  {
    path: 'profissional',
    data: { pageTitle: 'clinicaMultiTerapiasApp.profissional.home.title' },
    loadChildren: () => import('./profissional/profissional.routes'),
  },
  {
    path: 'prontuario',
    data: { pageTitle: 'clinicaMultiTerapiasApp.prontuario.home.title' },
    loadChildren: () => import('./prontuario/prontuario.routes'),
  },
  {
    path: 'sala',
    data: { pageTitle: 'clinicaMultiTerapiasApp.sala.home.title' },
    loadChildren: () => import('./sala/sala.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
