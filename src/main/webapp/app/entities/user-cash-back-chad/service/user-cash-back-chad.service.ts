import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserCashBackChad, NewUserCashBackChad } from '../user-cash-back-chad.model';

export type PartialUpdateUserCashBackChad = Partial<IUserCashBackChad> & Pick<IUserCashBackChad, 'id'>;

export type EntityResponseType = HttpResponse<IUserCashBackChad>;
export type EntityArrayResponseType = HttpResponse<IUserCashBackChad[]>;

@Injectable({ providedIn: 'root' })
export class UserCashBackChadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-cash-backs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userCashBack: NewUserCashBackChad): Observable<EntityResponseType> {
    return this.http.post<IUserCashBackChad>(this.resourceUrl, userCashBack, { observe: 'response' });
  }

  update(userCashBack: IUserCashBackChad): Observable<EntityResponseType> {
    return this.http.put<IUserCashBackChad>(`${this.resourceUrl}/${this.getUserCashBackChadIdentifier(userCashBack)}`, userCashBack, {
      observe: 'response',
    });
  }

  partialUpdate(userCashBack: PartialUpdateUserCashBackChad): Observable<EntityResponseType> {
    return this.http.patch<IUserCashBackChad>(`${this.resourceUrl}/${this.getUserCashBackChadIdentifier(userCashBack)}`, userCashBack, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserCashBackChad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserCashBackChad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUserCashBackChadIdentifier(userCashBack: Pick<IUserCashBackChad, 'id'>): number {
    return userCashBack.id;
  }

  compareUserCashBackChad(o1: Pick<IUserCashBackChad, 'id'> | null, o2: Pick<IUserCashBackChad, 'id'> | null): boolean {
    return o1 && o2 ? this.getUserCashBackChadIdentifier(o1) === this.getUserCashBackChadIdentifier(o2) : o1 === o2;
  }

  addUserCashBackChadToCollectionIfMissing<Type extends Pick<IUserCashBackChad, 'id'>>(
    userCashBackCollection: Type[],
    ...userCashBacksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const userCashBacks: Type[] = userCashBacksToCheck.filter(isPresent);
    if (userCashBacks.length > 0) {
      const userCashBackCollectionIdentifiers = userCashBackCollection.map(
        userCashBackItem => this.getUserCashBackChadIdentifier(userCashBackItem)!
      );
      const userCashBacksToAdd = userCashBacks.filter(userCashBackItem => {
        const userCashBackIdentifier = this.getUserCashBackChadIdentifier(userCashBackItem);
        if (userCashBackCollectionIdentifiers.includes(userCashBackIdentifier)) {
          return false;
        }
        userCashBackCollectionIdentifiers.push(userCashBackIdentifier);
        return true;
      });
      return [...userCashBacksToAdd, ...userCashBackCollection];
    }
    return userCashBackCollection;
  }
}
