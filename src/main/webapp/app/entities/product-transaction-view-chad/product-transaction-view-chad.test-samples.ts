import dayjs from 'dayjs/esm';

import { TypeTransaction } from 'app/entities/enumerations/type-transaction.model';

import { IProductTransactionViewChad, NewProductTransactionViewChad } from './product-transaction-view-chad.model';

export const sampleWithRequiredData: IProductTransactionViewChad = {
  id: 94280,
};

export const sampleWithPartialData: IProductTransactionViewChad = {
  id: 4811,
  productName: 'logistical Tasty auxiliary',
  stockName: 'action-items',
  description: 'mindshare Buckinghamshire',
  createdBy: 'SMS Producteur',
  lastModifiedDate: dayjs('2022-11-02T21:18'),
};

export const sampleWithFullData: IProductTransactionViewChad = {
  id: 55689,
  productName: 'CSS gold',
  stockName: 'a Dong',
  quantite: 28492,
  transactionType: TypeTransaction['SALECANCELLED'],
  description: 'Investment payment c',
  createdBy: 'efficient maroon',
  createdDate: dayjs('2022-11-03T05:21'),
  lastModifiedBy: 'Égypte firewall',
  lastModifiedDate: dayjs('2022-11-02T19:16'),
};

export const sampleWithNewData: NewProductTransactionViewChad = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
