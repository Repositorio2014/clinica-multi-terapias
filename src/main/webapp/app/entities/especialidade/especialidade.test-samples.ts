import { IEspecialidade, NewEspecialidade } from './especialidade.model';

export const sampleWithRequiredData: IEspecialidade = {
  id: 32504,
  nome: 'NEUROPSICOLOGIA',
};

export const sampleWithPartialData: IEspecialidade = {
  id: 23340,
  nome: 'PSICOLOGIA',
  descricao: 'infatuated perfection unruly',
};

export const sampleWithFullData: IEspecialidade = {
  id: 11148,
  nome: 'NEUROPSICOLOGIA',
  descricao: 'truly who beard',
};

export const sampleWithNewData: NewEspecialidade = {
  nome: 'NEUROPSICOLOGIA',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
