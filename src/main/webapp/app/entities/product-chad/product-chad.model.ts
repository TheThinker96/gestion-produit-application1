export interface IProductChad {
  id: number;
  name?: string | null;
  prix?: number | null;
}

export type NewProductChad = Omit<IProductChad, 'id'> & { id: null };
