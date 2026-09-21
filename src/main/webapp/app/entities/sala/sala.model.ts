export interface ISala {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  capacidade?: number | null;
}

export type NewSala = Omit<ISala, 'id'> & { id: null };
