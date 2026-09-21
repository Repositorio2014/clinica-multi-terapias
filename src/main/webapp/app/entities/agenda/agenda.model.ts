import dayjs from 'dayjs/esm';
import { IPaciente } from 'app/entities/paciente/paciente.model';
import { IProfissional } from 'app/entities/profissional/profissional.model';
import { ISala } from 'app/entities/sala/sala.model';
import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { StatusAgendamento } from 'app/entities/enumerations/status-agendamento.model';

export interface IAgenda {
  id: number;
  dataHoraInicio?: dayjs.Dayjs | null;
  dataHoraFim?: dayjs.Dayjs | null;
  status?: keyof typeof StatusAgendamento | null;
  observacoes?: string | null;
  valorCobrado?: number | null;
  paciente?: Pick<IPaciente, 'id' | 'nome'> | null;
  profissional?: Pick<IProfissional, 'id' | 'nome'> | null;
  sala?: Pick<ISala, 'id' | 'nome'> | null;
  especialidade?: Pick<IEspecialidade, 'id' | 'nome'> | null;
}

export type NewAgenda = Omit<IAgenda, 'id'> & { id: null };
