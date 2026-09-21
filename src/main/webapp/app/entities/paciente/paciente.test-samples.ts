import dayjs from 'dayjs/esm';

import { IPaciente, NewPaciente } from './paciente.model';

export const sampleWithRequiredData: IPaciente = {
  id: 28812,
  nome: 'translation',
  cpf: 'excess solvency overconfidently',
  dataNascimento: dayjs('2026-08-31'),
  telefone: 'fooey',
  ativo: true,
};

export const sampleWithPartialData: IPaciente = {
  id: 26196,
  nome: 'quickly gracefully',
  cpf: 'forgery inexperienced aggravating',
  dataNascimento: dayjs('2026-08-31'),
  telefone: 'wherever',
  nomeResponsavel: 'hmph',
  telefoneResponsavel: 'through',
  observacoes: '../fake-data/blob/hipster.txt',
  ativo: false,
};

export const sampleWithFullData: IPaciente = {
  id: 9869,
  nome: 'for',
  cpf: 'hastily uh-huh nicely',
  dataNascimento: dayjs('2026-08-31'),
  telefone: 'roger mortar knight',
  email: 'Isis_Braga43@gmail.com',
  nomeResponsavel: 'vastly psst',
  telefoneResponsavel: 'astride',
  endereco: 'concentration whoa concerning',
  convenio: 'nerve morning',
  observacoes: '../fake-data/blob/hipster.txt',
  ativo: false,
};

export const sampleWithNewData: NewPaciente = {
  nome: 'uh-huh daintily more',
  cpf: 'meh upright',
  dataNascimento: dayjs('2026-08-31'),
  telefone: 'equal',
  ativo: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
