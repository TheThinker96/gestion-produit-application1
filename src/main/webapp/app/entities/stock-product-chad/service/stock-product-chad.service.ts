import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStockProductChad, NewStockProductChad } from '../stock-product-chad.model';

export type PartialUpdateStockProductChad = Partial<IStockProductChad> & Pick<IStockProductChad, 'id'>;

type RestOf<T extends IStockProductChad | NewStockProductChad> = Omit<T, 'deliveryDate' | 'expirationDate'> & {
  deliveryDate?: string | null;
  expirationDate?: string | null;
};

export type RestStockProductChad = RestOf<IStockProductChad>;

export type NewRestStockProductChad = RestOf<NewStockProductChad>;

export type PartialUpdateRestStockProductChad = RestOf<PartialUpdateStockProductChad>;

export type EntityResponseType = HttpResponse<IStockProductChad>;
export type EntityArrayResponseType = HttpResponse<IStockProductChad[]>;

@Injectable({ providedIn: 'root' })
export class StockProductChadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/stock-products');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(stockProduct: NewStockProductChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockProduct);
    return this.http
      .post<RestStockProductChad>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(stockProduct: IStockProductChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockProduct);
    return this.http
      .put<RestStockProductChad>(`${this.resourceUrl}/${this.getStockProductChadIdentifier(stockProduct)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(stockProduct: PartialUpdateStockProductChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockProduct);
    return this.http
      .patch<RestStockProductChad>(`${this.resourceUrl}/${this.getStockProductChadIdentifier(stockProduct)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestStockProductChad>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestStockProductChad[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStockProductChadIdentifier(stockProduct: Pick<IStockProductChad, 'id'>): number {
    return stockProduct.id;
  }

  compareStockProductChad(o1: Pick<IStockProductChad, 'id'> | null, o2: Pick<IStockProductChad, 'id'> | null): boolean {
    return o1 && o2 ? this.getStockProductChadIdentifier(o1) === this.getStockProductChadIdentifier(o2) : o1 === o2;
  }

  addStockProductChadToCollectionIfMissing<Type extends Pick<IStockProductChad, 'id'>>(
    stockProductCollection: Type[],
    ...stockProductsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const stockProducts: Type[] = stockProductsToCheck.filter(isPresent);
    if (stockProducts.length > 0) {
      const stockProductCollectionIdentifiers = stockProductCollection.map(
        stockProductItem => this.getStockProductChadIdentifier(stockProductItem)!
      );
      const stockProductsToAdd = stockProducts.filter(stockProductItem => {
        const stockProductIdentifier = this.getStockProductChadIdentifier(stockProductItem);
        if (stockProductCollectionIdentifiers.includes(stockProductIdentifier)) {
          return false;
        }
        stockProductCollectionIdentifiers.push(stockProductIdentifier);
        return true;
      });
      return [...stockProductsToAdd, ...stockProductCollection];
    }
    return stockProductCollection;
  }

  protected convertDateFromClient<T extends IStockProductChad | NewStockProductChad | PartialUpdateStockProductChad>(
    stockProduct: T
  ): RestOf<T> {
    return {
      ...stockProduct,
      deliveryDate: stockProduct.deliveryDate?.toJSON() ?? null,
      expirationDate: stockProduct.expirationDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restStockProductChad: RestStockProductChad): IStockProductChad {
    return {
      ...restStockProductChad,
      deliveryDate: restStockProductChad.deliveryDate ? dayjs(restStockProductChad.deliveryDate) : undefined,
      expirationDate: restStockProductChad.expirationDate ? dayjs(restStockProductChad.expirationDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestStockProductChad>): HttpResponse<IStockProductChad> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestStockProductChad[]>): HttpResponse<IStockProductChad[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
