import { UserSaleAccountStatut } from 'app/entities/enumerations/user-sale-account-statut.model';

export interface IUserCashBackChad {
  id: number;
  montant?: number | null;
  balance?: number | null;
  statut?: UserSaleAccountStatut | null;
}

export type NewUserCashBackChad = Omit<IUserCashBackChad, 'id'> & { id: null };
