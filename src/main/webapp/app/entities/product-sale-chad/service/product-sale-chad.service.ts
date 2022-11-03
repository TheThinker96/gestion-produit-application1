import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductSaleChad, NewProductSaleChad } from '../product-sale-chad.model';

export type PartialUpdateProductSaleChad = Partial<IProductSaleChad> & Pick<IProductSaleChad, 'id'>;

export type EntityResponseType = HttpResponse<IProductSaleChad>;
export type EntityArrayResponseType = HttpResponse<IProductSaleChad[]>;

@Injectable({ providedIn: 'root' })
export class ProductSaleChadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-sales');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productSale: NewProductSaleChad): Observable<EntityResponseType> {
    return this.http.post<IProductSaleChad>(this.resourceUrl, productSale, { observe: 'response' });
  }

  update(productSale: IProductSaleChad): Observable<EntityResponseType> {
    return this.http.put<IProductSaleChad>(`${this.resourceUrl}/${this.getProductSaleChadIdentifier(productSale)}`, productSale, {
      observe: 'response',
    });
  }

  partialUpdate(productSale: PartialUpdateProductSaleChad): Observable<EntityResponseType> {
    return this.http.patch<IProductSaleChad>(`${this.resourceUrl}/${this.getProductSaleChadIdentifier(productSale)}`, productSale, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductSaleChad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductSaleChad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductSaleChadIdentifier(productSale: Pick<IProductSaleChad, 'id'>): number {
    return productSale.id;
  }

  compareProductSaleChad(o1: Pick<IProductSaleChad, 'id'> | null, o2: Pick<IProductSaleChad, 'id'> | null): boolean {
    return o1 && o2 ? this.getProductSaleChadIdentifier(o1) === this.getProductSaleChadIdentifier(o2) : o1 === o2;
  }

  addProductSaleChadToCollectionIfMissing<Type extends Pick<IProductSaleChad, 'id'>>(
    productSaleCollection: Type[],
    ...productSalesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productSales: Type[] = productSalesToCheck.filter(isPresent);
    if (productSales.length > 0) {
      const productSaleCollectionIdentifiers = productSaleCollection.map(
        productSaleItem => this.getProductSaleChadIdentifier(productSaleItem)!
      );
      const productSalesToAdd = productSales.filter(productSaleItem => {
        const productSaleIdentifier = this.getProductSaleChadIdentifier(productSaleItem);
        if (productSaleCollectionIdentifiers.includes(productSaleIdentifier)) {
          return false;
        }
        productSaleCollectionIdentifiers.push(productSaleIdentifier);
        return true;
      });
      return [...productSalesToAdd, ...productSaleCollection];
    }
    return productSaleCollection;
  }
}
