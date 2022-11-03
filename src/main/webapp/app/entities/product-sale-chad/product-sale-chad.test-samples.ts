import { IProductSaleChad, NewProductSaleChad } from './product-sale-chad.model';

export const sampleWithRequiredData: IProductSaleChad = {
  id: 41509,
  quantite: 23110,
  prixTotal: 97120,
  statut: false,
};

export const sampleWithPartialData: IProductSaleChad = {
  id: 14148,
  quantite: 95137,
  prixTotal: 94212,
  statut: true,
};

export const sampleWithFullData: IProductSaleChad = {
  id: 89272,
  quantite: 21096,
  prixTotal: 90587,
  statut: true,
};

export const sampleWithNewData: NewProductSaleChad = {
  quantite: 13720,
  prixTotal: 28309,
  statut: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
