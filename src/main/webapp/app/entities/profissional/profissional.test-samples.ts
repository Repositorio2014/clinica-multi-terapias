import { IProfissional, NewProfissional } from './profissional.model';

export const sampleWithRequiredData: IProfissional = {
  id: 10443,
  nome: 'attribute meh',
  cpf: 'out',
  registroConselho: 'consequently disapprove during',
  telefone: 'mask',
  email: 'MariaClara.Barros@yahoo.com',
  ativo: true,
};

export const sampleWithPartialData: IProfissional = {
  id: 5045,
  nome: 'sonata unlawful',
  cpf: 'hence brr',
  registroConselho: 'notarize furthermore',
  telefone: 'however why',
  email: 'Tertuliano.Moreira@yahoo.com',
  ativo: true,
  valorSessao: 24952.54,
};

export const sampleWithFullData: IProfissional = {
  id: 400,
  nome: 'solemnly form',
  cpf: 'eek',
  registroConselho: 'glider',
  telefone: 'whose opposite',
  email: 'Bryan35@bol.com.br',
  ativo: false,
  valorSessao: 6355.18,
};

export const sampleWithNewData: NewProfissional = {
  nome: 'supposing round jaywalk',
  cpf: 'summary limply',
  registroConselho: 'illusion',
  telefone: 'foretell brown whoa',
  email: 'Aline95@bol.com.br',
  ativo: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
