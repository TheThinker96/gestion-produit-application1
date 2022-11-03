import dayjs from 'dayjs/esm';

export interface IStockProductViewChad {
  id: number;
  quantite?: number | null;
  stockName?: string | null;
  productName?: string | null;
  deliveryDate?: dayjs.Dayjs | null;
  expirationDate?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewStockProductViewChad = Omit<IStockProductViewChad, 'id'> & { id: null };
