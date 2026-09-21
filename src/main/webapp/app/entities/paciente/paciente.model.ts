import dayjs from 'dayjs/esm';

export interface IPaciente {
  id: number;
  nome?: string | null;
  cpf?: string | null;
  dataNascimento?: dayjs.Dayjs | null;
  telefone?: string | null;
  email?: string | null;
  nomeResponsavel?: string | null;
  telefoneResponsavel?: string | null;
  endereco?: string | null;
  convenio?: string | null;
  observacoes?: string | null;
  ativo?: boolean | null;
}

export type NewPaciente = Omit<IPaciente, 'id'> & { id: null };
