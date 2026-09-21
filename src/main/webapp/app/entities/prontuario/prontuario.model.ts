import dayjs from 'dayjs/esm';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { IProfissional } from 'app/entities/profissional/profissional.model';
import { IAgenda } from 'app/entities/agenda/agenda.model';
import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { TipoProntuario } from 'app/entities/enumerations/tipo-prontuario.model';

export interface IProntuario {
  id: number;
  tipo?: keyof typeof TipoProntuario | null;
  dataAtendimento?: dayjs.Dayjs | null;
  titulo?: string | null;
  conteudo?: string | null;
  confidencial?: boolean | null;
  paciente?: Pick<IPaciente, 'id' | 'nome'> | null;
  profissional?: Pick<IProfissional, 'id' | 'nome'> | null;
  agenda?: Pick<IAgenda, 'id'> | null;
  especialidade?: Pick<IEspecialidade, 'id' | 'nome'> | null;
}

export type NewProntuario = Omit<IProntuario, 'id'> & { id: null };
