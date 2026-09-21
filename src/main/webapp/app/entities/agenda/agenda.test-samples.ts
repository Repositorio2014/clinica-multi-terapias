import dayjs from 'dayjs/esm';

import { IAgenda, NewAgenda } from './agenda.model';

export const sampleWithRequiredData: IAgenda = {
  id: 9241,
  dataHoraInicio: dayjs('2026-08-31T21:48'),
  dataHoraFim: dayjs('2026-08-31T06:02'),
  status: 'CANCELADO',
};

export const sampleWithPartialData: IAgenda = {
  id: 29298,
  dataHoraInicio: dayjs('2026-08-31T05:44'),
  dataHoraFim: dayjs('2026-08-31T11:31'),
  status: 'AGENDADO',
  observacoes: 'brr doubtfully',
  valorCobrado: 19482.63,
};

export const sampleWithFullData: IAgenda = {
  id: 1151,
  dataHoraInicio: dayjs('2026-08-31T15:37'),
  dataHoraFim: dayjs('2026-08-31T13:00'),
  status: 'FALTA',
  observacoes: 'seldom shakily',
  valorCobrado: 17035.94,
};

export const sampleWithNewData: NewAgenda = {
  dataHoraInicio: dayjs('2026-08-31T09:28'),
  dataHoraFim: dayjs('2026-08-31T22:59'),
  status: 'CONFIRMADO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
