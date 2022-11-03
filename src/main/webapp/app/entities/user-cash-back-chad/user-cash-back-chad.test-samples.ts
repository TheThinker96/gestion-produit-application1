import { UserSaleAccountStatut } from 'app/entities/enumerations/user-sale-account-statut.model';

import { IUserCashBackChad, NewUserCashBackChad } from './user-cash-back-chad.model';

export const sampleWithRequiredData: IUserCashBackChad = {
  id: 93813,
  montant: 48557,
  balance: 46184,
  statut: UserSaleAccountStatut['GONE'],
};

export const sampleWithPartialData: IUserCashBackChad = {
  id: 60404,
  montant: 55409,
  balance: 12503,
  statut: UserSaleAccountStatut['ACTIVE'],
};

export const sampleWithFullData: IUserCashBackChad = {
  id: 92315,
  montant: 64164,
  balance: 74232,
  statut: UserSaleAccountStatut['ACTIVE'],
};

export const sampleWithNewData: NewUserCashBackChad = {
  montant: 83211,
  balance: 65659,
  statut: UserSaleAccountStatut['ACTIVE'],
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
