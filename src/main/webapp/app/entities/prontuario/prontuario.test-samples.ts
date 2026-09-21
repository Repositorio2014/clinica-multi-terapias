import dayjs from 'dayjs/esm';

import { IProntuario, NewProntuario } from './prontuario.model';

export const sampleWithRequiredData: IProntuario = {
  id: 28171,
  tipo: 'LAUDO',
  dataAtendimento: dayjs('2026-08-31T23:17'),
  titulo: 'buzzing anxiously',
  conteudo: '../fake-data/blob/hipster.txt',
  confidencial: false,
};

export const sampleWithPartialData: IProntuario = {
  id: 26413,
  tipo: 'ENCAMINHAMENTO',
  dataAtendimento: dayjs('2026-08-31T22:31'),
  titulo: 'measly',
  conteudo: '../fake-data/blob/hipster.txt',
  confidencial: true,
};

export const sampleWithFullData: IProntuario = {
  id: 25676,
  tipo: 'EVOLUCAO',
  dataAtendimento: dayjs('2026-08-31T15:08'),
  titulo: 'term presume',
  conteudo: '../fake-data/blob/hipster.txt',
  confidencial: false,
};

export const sampleWithNewData: NewProntuario = {
  tipo: 'LAUDO',
  dataAtendimento: dayjs('2026-08-31T20:14'),
  titulo: 'whether bleach around',
  conteudo: '../fake-data/blob/hipster.txt',
  confidencial: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
