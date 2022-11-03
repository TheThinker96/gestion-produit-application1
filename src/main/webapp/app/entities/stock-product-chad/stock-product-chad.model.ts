import dayjs from 'dayjs/esm';
import { IProductChad } from 'app/entities/product-chad/product-chad.model';

export interface IStockProductChad {
  id: number;
  quantite?: number | null;
  name?: string | null;
  deliveryDate?: dayjs.Dayjs | null;
  expirationDate?: dayjs.Dayjs | null;
  prixStock?: number | null;
  product?: Pick<IProductChad, 'id'> | null;
}

export type NewStockProductChad = Omit<IStockProductChad, 'id'> & { id: null };
