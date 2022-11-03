import { UserSaleAccountStatut } from 'app/entities/enumerations/user-sale-account-statut.model';

import { IUserSaleAccountChad, NewUserSaleAccountChad } from './user-sale-account-chad.model';

export const sampleWithRequiredData: IUserSaleAccountChad = {
  id: 59577,
};

export const sampleWithPartialData: IUserSaleAccountChad = {
  id: 67207,
  statut: UserSaleAccountStatut['ACTIVE'],
  balance: 26397,
};

export const sampleWithFullData: IUserSaleAccountChad = {
  id: 83461,
  statut: UserSaleAccountStatut['GONE'],
  balance: 52718,
};

export const sampleWithNewData: NewUserSaleAccountChad = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
