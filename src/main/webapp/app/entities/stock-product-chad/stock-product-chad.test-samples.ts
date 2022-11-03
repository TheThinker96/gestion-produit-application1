import dayjs from 'dayjs/esm';

import { IStockProductChad, NewStockProductChad } from './stock-product-chad.model';

export const sampleWithRequiredData: IStockProductChad = {
  id: 404,
  name: 'deposit Small',
  deliveryDate: dayjs('2022-11-03T03:16'),
  expirationDate: dayjs('2022-11-03T14:13'),
  prixStock: 2836,
};

export const sampleWithPartialData: IStockProductChad = {
  id: 29856,
  quantite: 98725,
  name: 'Seamless Île-de-France firewall',
  deliveryDate: dayjs('2022-11-02T19:37'),
  expirationDate: dayjs('2022-11-02T21:15'),
  prixStock: 19071,
};

export const sampleWithFullData: IStockProductChad = {
  id: 56444,
  quantite: 63551,
  name: 'scalable Soft',
  deliveryDate: dayjs('2022-11-03T02:32'),
  expirationDate: dayjs('2022-11-02T16:38'),
  prixStock: 89411,
};

export const sampleWithNewData: NewStockProductChad = {
  name: 'Handcrafted',
  deliveryDate: dayjs('2022-11-03T01:54'),
  expirationDate: dayjs('2022-11-02T21:34'),
  prixStock: 10005,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
