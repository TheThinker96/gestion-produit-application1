import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductSaleViewChad, NewProductSaleViewChad } from '../product-sale-view-chad.model';

export type PartialUpdateProductSaleViewChad = Partial<IProductSaleViewChad> & Pick<IProductSaleViewChad, 'id'>;

type RestOf<T extends IProductSaleViewChad | NewProductSaleViewChad> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestProductSaleViewChad = RestOf<IProductSaleViewChad>;

export type NewRestProductSaleViewChad = RestOf<NewProductSaleViewChad>;

export type PartialUpdateRestProductSaleViewChad = RestOf<PartialUpdateProductSaleViewChad>;

export type EntityResponseType = HttpResponse<IProductSaleViewChad>;
export type EntityArrayResponseType = HttpResponse<IProductSaleViewChad[]>;

@Injectable({ providedIn: 'root' })
export class ProductSaleViewChadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-sale-views');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productSaleView: NewProductSaleViewChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSaleView);
    return this.http
      .post<RestProductSaleViewChad>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(productSaleView: IProductSaleViewChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSaleView);
    return this.http
      .put<RestProductSaleViewChad>(`${this.resourceUrl}/${this.getProductSaleViewChadIdentifier(productSaleView)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(productSaleView: PartialUpdateProductSaleViewChad): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(productSaleView);
    return this.http
      .patch<RestProductSaleViewChad>(`${this.resourceUrl}/${this.getProductSaleViewChadIdentifier(productSaleView)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestProductSaleViewChad>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestProductSaleViewChad[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductSaleViewChadIdentifier(productSaleView: Pick<IProductSaleViewChad, 'id'>): number {
    return productSaleView.id;
  }

  compareProductSaleViewChad(o1: Pick<IProductSaleViewChad, 'id'> | null, o2: Pick<IProductSaleViewChad, 'id'> | null): boolean {
    return o1 && o2 ? this.getProductSaleViewChadIdentifier(o1) === this.getProductSaleViewChadIdentifier(o2) : o1 === o2;
  }

  addProductSaleViewChadToCollectionIfMissing<Type extends Pick<IProductSaleViewChad, 'id'>>(
    productSaleViewCollection: Type[],
    ...productSaleViewsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productSaleViews: Type[] = productSaleViewsToCheck.filter(isPresent);
    if (productSaleViews.length > 0) {
      const productSaleViewCollectionIdentifiers = productSaleViewCollection.map(
        productSaleViewItem => this.getProductSaleViewChadIdentifier(productSaleViewItem)!
      );
      const productSaleViewsToAdd = productSaleViews.filter(productSaleViewItem => {
        const productSaleViewIdentifier = this.getProductSaleViewChadIdentifier(productSaleViewItem);
        if (productSaleViewCollectionIdentifiers.includes(productSaleViewIdentifier)) {
          return false;
        }
        productSaleViewCollectionIdentifiers.push(productSaleViewIdentifier);
        return true;
      });
      return [...productSaleViewsToAdd, ...productSaleViewCollection];
    }
    return productSaleViewCollection;
  }

  protected convertDateFromClient<T extends IProductSaleViewChad | NewProductSaleViewChad | PartialUpdateProductSaleViewChad>(
    productSaleView: T
  ): RestOf<T> {
    return {
      ...productSaleView,
      createdDate: productSaleView.createdDate?.toJSON() ?? null,
      lastModifiedDate: productSaleView.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restProductSaleViewChad: RestProductSaleViewChad): IProductSaleViewChad {
    return {
      ...restProductSaleViewChad,
      createdDate: restProductSaleViewChad.createdDate ? dayjs(restProductSaleViewChad.createdDate) : undefined,
      lastModifiedDate: restProductSaleViewChad.lastModifiedDate ? dayjs(restProductSaleViewChad.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestProductSaleViewChad>): HttpResponse<IProductSaleViewChad> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestProductSaleViewChad[]>): HttpResponse<IProductSaleViewChad[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
