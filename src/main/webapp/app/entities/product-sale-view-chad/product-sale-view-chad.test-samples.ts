import dayjs from 'dayjs/esm';

import { IProductSaleViewChad, NewProductSaleViewChad } from './product-sale-view-chad.model';

export const sampleWithRequiredData: IProductSaleViewChad = {
  id: 84151,
};

export const sampleWithPartialData: IProductSaleViewChad = {
  id: 99769,
  quantite: 34264,
  productPrice: 68730,
  createdDate: dayjs('2022-11-03T14:07'),
  lastModifiedDate: dayjs('2022-11-03T10:25'),
};

export const sampleWithFullData: IProductSaleViewChad = {
  id: 32127,
  productName: 'TCP',
  stockName: 'program Corse connect',
  quantite: 20159,
  productPrice: 11477,
  total: 49503,
  createdBy: 'Shoes Cheese',
  createdDate: dayjs('2022-11-03T06:01'),
  lastModifiedBy: 'b online',
  lastModifiedDate: dayjs('2022-11-03T05:00'),
};

export const sampleWithNewData: NewProductSaleViewChad = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
