import dayjs from 'dayjs/esm';
import { TypeTransaction } from 'app/entities/enumerations/type-transaction.model';

export interface IProductTransactionViewChad {
  id: number;
  productName?: string | null;
  stockName?: string | null;
  quantite?: number | null;
  transactionType?: TypeTransaction | null;
  description?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewProductTransactionViewChad = Omit<IProductTransactionViewChad, 'id'> & { id: null };
