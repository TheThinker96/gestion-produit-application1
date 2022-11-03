import dayjs from 'dayjs/esm';

export interface IProductSaleViewChad {
  id: number;
  productName?: string | null;
  stockName?: string | null;
  quantite?: number | null;
  productPrice?: number | null;
  total?: number | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewProductSaleViewChad = Omit<IProductSaleViewChad, 'id'> & { id: null };
