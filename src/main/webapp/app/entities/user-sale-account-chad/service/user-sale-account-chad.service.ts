import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserSaleAccountChad, NewUserSaleAccountChad } from '../user-sale-account-chad.model';

export type PartialUpdateUserSaleAccountChad = Partial<IUserSaleAccountChad> & Pick<IUserSaleAccountChad, 'id'>;

export type EntityResponseType = HttpResponse<IUserSaleAccountChad>;
export type EntityArrayResponseType = HttpResponse<IUserSaleAccountChad[]>;

@Injectable({ providedIn: 'root' })
export class UserSaleAccountChadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-sale-accounts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userSaleAccount: NewUserSaleAccountChad): Observable<EntityResponseType> {
    return this.http.post<IUserSaleAccountChad>(this.resourceUrl, userSaleAccount, { observe: 'response' });
  }

  update(userSaleAccount: IUserSaleAccountChad): Observable<EntityResponseType> {
    return this.http.put<IUserSaleAccountChad>(
      `${this.resourceUrl}/${this.getUserSaleAccountChadIdentifier(userSaleAccount)}`,
      userSaleAccount,
      { observe: 'response' }
    );
  }

  partialUpdate(userSaleAccount: PartialUpdateUserSaleAccountChad): Observable<EntityResponseType> {
    return this.http.patch<IUserSaleAccountChad>(
      `${this.resourceUrl}/${this.getUserSaleAccountChadIdentifier(userSaleAccount)}`,
      userSaleAccount,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserSaleAccountChad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserSaleAccountChad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUserSaleAccountChadIdentifier(userSaleAccount: Pick<IUserSaleAccountChad, 'id'>): number {
    return userSaleAccount.id;
  }

  compareUserSaleAccountChad(o1: Pick<IUserSaleAccountChad, 'id'> | null, o2: Pick<IUserSaleAccountChad, 'id'> | null): boolean {
    return o1 && o2 ? this.getUserSaleAccountChadIdentifier(o1) === this.getUserSaleAccountChadIdentifier(o2) : o1 === o2;
  }

  addUserSaleAccountChadToCollectionIfMissing<Type extends Pick<IUserSaleAccountChad, 'id'>>(
    userSaleAccountCollection: Type[],
    ...userSaleAccountsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const userSaleAccounts: Type[] = userSaleAccountsToCheck.filter(isPresent);
    if (userSaleAccounts.length > 0) {
      const userSaleAccountCollectionIdentifiers = userSaleAccountCollection.map(
        userSaleAccountItem => this.getUserSaleAccountChadIdentifier(userSaleAccountItem)!
      );
      const userSaleAccountsToAdd = userSaleAccounts.filter(userSaleAccountItem => {
        const userSaleAccountIdentifier = this.getUserSaleAccountChadIdentifier(userSaleAccountItem);
        if (userSaleAccountCollectionIdentifiers.includes(userSaleAccountIdentifier)) {
          return false;
        }
        userSaleAccountCollectionIdentifiers.push(userSaleAccountIdentifier);
        return true;
      });
      return [...userSaleAccountsToAdd, ...userSaleAccountCollection];
    }
    return userSaleAccountCollection;
  }
}
