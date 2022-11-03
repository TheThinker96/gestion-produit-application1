import dayjs from 'dayjs/esm';

import { IStockProductViewChad, NewStockProductViewChad } from './stock-product-view-chad.model';

export const sampleWithRequiredData: IStockProductViewChad = {
  id: 31159,
};

export const sampleWithPartialData: IStockProductViewChad = {
  id: 94791,
  deliveryDate: dayjs('2022-11-03T10:52'),
  expirationDate: dayjs('2022-11-03T07:19'),
  createdBy: 'Configurable',
};

export const sampleWithFullData: IStockProductViewChad = {
  id: 44851,
  quantite: 20032,
  stockName: 'HTTP Investment Small',
  productName: 'Sénégal Cheese reintermediate',
  deliveryDate: dayjs('2022-11-02T16:39'),
  expirationDate: dayjs('2022-11-02T15:21'),
  createdBy: 'synthesize',
  createdDate: dayjs('2022-11-03T03:44'),
  lastModifiedBy: 'orchestrate Salad homogeneous',
  lastModifiedDate: dayjs('2022-11-02T21:02'),
};

export const sampleWithNewData: NewStockProductViewChad = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
