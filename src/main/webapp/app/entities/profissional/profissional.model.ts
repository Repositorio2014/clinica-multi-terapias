import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';

export interface IProfissional {
  id: number;
  nome?: string | null;
  cpf?: string | null;
  registroConselho?: string | null;
  telefone?: string | null;
  email?: string | null;
  ativo?: boolean | null;
  valorSessao?: number | null;
  especialidades?: Pick<IEspecialidade, 'id'>[] | null;
}

export type NewProfissional = Omit<IProfissional, 'id'> & { id: null };
