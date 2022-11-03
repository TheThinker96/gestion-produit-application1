import { IProductChad } from 'app/entities/product-chad/product-chad.model';
import { IStockProductChad } from 'app/entities/stock-product-chad/stock-product-chad.model';
import { IUserSaleAccountChad } from 'app/entities/user-sale-account-chad/user-sale-account-chad.model';

export interface IProductSaleChad {
  id: number;
  quantite?: number | null;
  prixTotal?: number | null;
  statut?: boolean | null;
  product?: Pick<IProductChad, 'id'> | null;
  stockProduct?: Pick<IStockProductChad, 'id'> | null;
  userSaleAccount?: Pick<IUserSaleAccountChad, 'id'> | null;
}

export type NewProductSaleChad = Omit<IProductSaleChad, 'id'> & { id: null };
