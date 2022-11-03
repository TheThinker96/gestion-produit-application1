import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductTransactionViewChad, NewProductTransactionViewChad } from '../product-transaction-view-chad.model';

export type PartialUpdateProductTransactionViewChad = Partial<IProductTransactionViewChad> & Pick<IProductTransactionViewChad, 'id'>;

type RestOf<T extends IProductTransactionViewChad | NewProductTransactionViewChad> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestProductTransactionViewChad = RestOf<IProductTransactionViewChad>;

export type NewRestProductTransactionViewChad = RestOf<NewProductTransactionViewChad>;

export type PartialUpdateRestProductTransactionViewChad = RestOf<PartialUpdateProductTransactionViewChad>;

export type EntityResponseType = HttpResponse<IProductTransactionViewChad>;
export type EntityArrayResponseType = HttpResponse<IProductTransactionViewChad[]>;

@Injectable({ providedIn: 'root' })
export class ProductTransactionViewChadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-transaction-views');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productTransactionView: NewProductTransactionViewChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productTransactionView);
    return this.http
      .post<RestProductTransactionViewChad>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(productTransactionView: IProductTransactionViewChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productTransactionView);
    return this.http
      .put<RestProductTransactionViewChad>(
        `${this.resourceUrl}/${this.getProductTransactionViewChadIdentifier(productTransactionView)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(productTransactionView: PartialUpdateProductTransactionViewChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productTransactionView);
    return this.http
      .patch<RestProductTransactionViewChad>(
        `${this.resourceUrl}/${this.getProductTransactionViewChadIdentifier(productTransactionView)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProductTransactionViewChad>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProductTransactionViewChad[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductTransactionViewChadIdentifier(productTransactionView: Pick<IProductTransactionViewChad, 'id'>): number {
    return productTransactionView.id;
  }

  compareProductTransactionViewChad(
    o1: Pick<IProductTransactionViewChad, 'id'> | null,
    o2: Pick<IProductTransactionViewChad, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getProductTransactionViewChadIdentifier(o1) === this.getProductTransactionViewChadIdentifier(o2) : o1 === o2;
  }

  addProductTransactionViewChadToCollectionIfMissing<Type extends Pick<IProductTransactionViewChad, 'id'>>(
    productTransactionViewCollection: Type[],
    ...productTransactionViewsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productTransactionViews: Type[] = productTransactionViewsToCheck.filter(isPresent);
    if (productTransactionViews.length > 0) {
      const productTransactionViewCollectionIdentifiers = productTransactionViewCollection.map(
        productTransactionViewItem => this.getProductTransactionViewChadIdentifier(productTransactionViewItem)!
      );
      const productTransactionViewsToAdd = productTransactionViews.filter(productTransactionViewItem => {
        const productTransactionViewIdentifier = this.getProductTransactionViewChadIdentifier(productTransactionViewItem);
        if (productTransactionViewCollectionIdentifiers.includes(productTransactionViewIdentifier)) {
          return false;
        }
        productTransactionViewCollectionIdentifiers.push(productTransactionViewIdentifier);
        return true;
      });
      return [...productTransactionViewsToAdd, ...productTransactionViewCollection];
    }
    return productTransactionViewCollection;
  }

  protected convertDateFromClient<
    T extends IProductTransactionViewChad | NewProductTransactionViewChad | PartialUpdateProductTransactionViewChad
  >(productTransactionView: T): RestOf<T> {
    return {
      ...productTransactionView,
      createdDate: productTransactionView.createdDate?.toJSON() ?? null,
      lastModifiedDate: productTransactionView.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProductTransactionViewChad: RestProductTransactionViewChad): IProductTransactionViewChad {
    return {
      ...restProductTransactionViewChad,
      createdDate: restProductTransactionViewChad.createdDate ? dayjs(restProductTransactionViewChad.createdDate) : undefined,
      lastModifiedDate: restProductTransactionViewChad.lastModifiedDate
        ? dayjs(restProductTransactionViewChad.lastModifiedDate)
        : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProductTransactionViewChad>): HttpResponse<IProductTransactionViewChad> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestProductTransactionViewChad[]>
  ): HttpResponse<IProductTransactionViewChad[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
