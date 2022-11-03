import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStockProductViewChad, NewStockProductViewChad } from '../stock-product-view-chad.model';

export type PartialUpdateStockProductViewChad = Partial<IStockProductViewChad> & Pick<IStockProductViewChad, 'id'>;

type RestOf<T extends IStockProductViewChad | NewStockProductViewChad> = Omit<
  T,
  'deliveryDate' | 'expirationDate' | 'createdDate' | 'lastModifiedDate'
> & {
  deliveryDate?: string | null;
  expirationDate?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestStockProductViewChad = RestOf<IStockProductViewChad>;

export type NewRestStockProductViewChad = RestOf<NewStockProductViewChad>;

export type PartialUpdateRestStockProductViewChad = RestOf<PartialUpdateStockProductViewChad>;

export type EntityResponseType = HttpResponse<IStockProductViewChad>;
export type EntityArrayResponseType = HttpResponse<IStockProductViewChad[]>;

@Injectable({ providedIn: 'root' })
export class StockProductViewChadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stock-product-views');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(stockProductView: NewStockProductViewChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockProductView);
    return this.http
      .post<RestStockProductViewChad>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(stockProductView: IStockProductViewChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockProductView);
    return this.http
      .put<RestStockProductViewChad>(`${this.resourceUrl}/${this.getStockProductViewChadIdentifier(stockProductView)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(stockProductView: PartialUpdateStockProductViewChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockProductView);
    return this.http
      .patch<RestStockProductViewChad>(`${this.resourceUrl}/${this.getStockProductViewChadIdentifier(stockProductView)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestStockProductViewChad>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestStockProductViewChad[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStockProductViewChadIdentifier(stockProductView: Pick<IStockProductViewChad, 'id'>): number {
    return stockProductView.id;
  }

  compareStockProductViewChad(o1: Pick<IStockProductViewChad, 'id'> | null, o2: Pick<IStockProductViewChad, 'id'> | null): boolean {
    return o1 && o2 ? this.getStockProductViewChadIdentifier(o1) === this.getStockProductViewChadIdentifier(o2) : o1 === o2;
  }

  addStockProductViewChadToCollectionIfMissing<Type extends Pick<IStockProductViewChad, 'id'>>(
    stockProductViewCollection: Type[],
    ...stockProductViewsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const stockProductViews: Type[] = stockProductViewsToCheck.filter(isPresent);
    if (stockProductViews.length > 0) {
      const stockProductViewCollectionIdentifiers = stockProductViewCollection.map(
        stockProductViewItem => this.getStockProductViewChadIdentifier(stockProductViewItem)!
      );
      const stockProductViewsToAdd = stockProductViews.filter(stockProductViewItem => {
        const stockProductViewIdentifier = this.getStockProductViewChadIdentifier(stockProductViewItem);
        if (stockProductViewCollectionIdentifiers.includes(stockProductViewIdentifier)) {
          return false;
        }
        stockProductViewCollectionIdentifiers.push(stockProductViewIdentifier);
        return true;
      });
      return [...stockProductViewsToAdd, ...stockProductViewCollection];
    }
    return stockProductViewCollection;
  }

  protected convertDateFromClient<T extends IStockProductViewChad | NewStockProductViewChad | PartialUpdateStockProductViewChad>(
    stockProductView: T
  ): RestOf<T> {
    return {
      ...stockProductView,
      deliveryDate: stockProductView.deliveryDate?.toJSON() ?? null,
      expirationDate: stockProductView.expirationDate?.toJSON() ?? null,
      createdDate: stockProductView.createdDate?.toJSON() ?? null,
      lastModifiedDate: stockProductView.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restStockProductViewChad: RestStockProductViewChad): IStockProductViewChad {
    return {
      ...restStockProductViewChad,
      deliveryDate: restStockProductViewChad.deliveryDate ? dayjs(restStockProductViewChad.deliveryDate) : undefined,
      expirationDate: restStockProductViewChad.expirationDate ? dayjs(restStockProductViewChad.expirationDate) : undefined,
      createdDate: restStockProductViewChad.createdDate ? dayjs(restStockProductViewChad.createdDate) : undefined,
      lastModifiedDate: restStockProductViewChad.lastModifiedDate ? dayjs(restStockProductViewChad.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestStockProductViewChad>): HttpResponse<IStockProductViewChad> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestStockProductViewChad[]>): HttpResponse<IStockProductViewChad[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
