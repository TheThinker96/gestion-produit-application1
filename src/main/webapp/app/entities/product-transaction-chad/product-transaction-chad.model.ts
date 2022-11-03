import { IStockProductChad } from 'app/entities/stock-product-chad/stock-product-chad.model';
import { IProductChad } from 'app/entities/product-chad/product-chad.model';
import { TypeTransaction } from 'app/entities/enumerations/type-transaction.model';

export interface IProductTransactionChad {
  id: number;
  transactionType?: TypeTransaction | null;
  description?: string | null;
  stockProduct?: Pick<IStockProductChad, 'id'> | null;
  product?: Pick<IProductChad, 'id'> | null;
}

export type NewProductTransactionChad = Omit<IProductTransactionChad, 'id'> & { id: null };
