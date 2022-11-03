import { TypeTransaction } from 'app/entities/enumerations/type-transaction.model';

import { IProductTransactionChad, NewProductTransactionChad } from './product-transaction-chad.model';

export const sampleWithRequiredData: IProductTransactionChad = {
  id: 37357,
  transactionType: TypeTransaction['SALECANCELLED'],
};

export const sampleWithPartialData: IProductTransactionChad = {
  id: 19902,
  transactionType: TypeTransaction['PRODUCTSALE'],
};

export const sampleWithFullData: IProductTransactionChad = {
  id: 37616,
  transactionType: TypeTransaction['SALECANCELLED'],
  description: 'Géorgie Reverse-engineered',
};

export const sampleWithNewData: NewProductTransactionChad = {
  transactionType: TypeTransaction['PRODUCTSALE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
