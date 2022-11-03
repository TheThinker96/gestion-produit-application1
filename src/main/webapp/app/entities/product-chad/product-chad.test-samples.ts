import { IProductChad, NewProductChad } from './product-chad.model';

export const sampleWithRequiredData: IProductChad = {
  id: 77672,
  name: 'Customer-focused',
};

export const sampleWithPartialData: IProductChad = {
  id: 78560,
  name: 'a Programmable',
  prix: 22539,
};

export const sampleWithFullData: IProductChad = {
  id: 49054,
  name: 'deliver',
  prix: 39641,
};

export const sampleWithNewData: NewProductChad = {
  name: 'models Saint-Dominique invoice',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
