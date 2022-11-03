import { UserSaleAccountStatut } from 'app/entities/enumerations/user-sale-account-statut.model';

export interface IUserSaleAccountChad {
  id: number;
  statut?: UserSaleAccountStatut | null;
  balance?: number | null;
}

export type NewUserSaleAccountChad = Omit<IUserSaleAccountChad, 'id'> & { id: null };
