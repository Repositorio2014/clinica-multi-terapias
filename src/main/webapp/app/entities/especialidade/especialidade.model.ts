import { IProfissional } from 'app/entities/profissional/profissional.model';
import { TipoEspecialidade } from 'app/entities/enumerations/tipo-especialidade.model';

export interface IEspecialidade {
  id: number;
  nome?: keyof typeof TipoEspecialidade | null;
  descricao?: string | null;
  profissionais?: Pick<IProfissional, 'id'>[] | null;
}

export type NewEspecialidade = Omit<IEspecialidade, 'id'> & { id: null };
