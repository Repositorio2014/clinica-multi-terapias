import { ISala, NewSala } from './sala.model';

export const sampleWithRequiredData: ISala = {
  id: 18251,
  nome: 'although',
};

export const sampleWithPartialData: ISala = {
  id: 31916,
  nome: 'putrid maestro unto',
};

export const sampleWithFullData: ISala = {
  id: 3715,
  nome: 'when pivot',
  descricao: 'into soggy accountability',
  capacidade: 17202,
};

export const sampleWithNewData: NewSala = {
  nome: 'wrongly boo yahoo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
