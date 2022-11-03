import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductChad, NewProductChad } from '../product-chad.model';

export type PartialUpdateProductChad = Partial<IProductChad> & Pick<IProductChad, 'id'>;

export type EntityResponseType = HttpResponse<IProductChad>;
export type EntityArrayResponseType = HttpResponse<IProductChad[]>;

@Injectable({ providedIn: 'root' })
export class ProductChadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/products');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(product: NewProductChad): Observable<EntityResponseType> {
    return this.http.post<IProductChad>(this.resourceUrl, product, { observe: 'response' });
  }

  update(product: IProductChad): Observable<EntityResponseType> {
    return this.http.put<IProductChad>(`${this.resourceUrl}/${this.getProductChadIdentifier(product)}`, product, { observe: 'response' });
  }

  partialUpdate(product: PartialUpdateProductChad): Observable<EntityResponseType> {
    return this.http.patch<IProductChad>(`${this.resourceUrl}/${this.getProductChadIdentifier(product)}`, product, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductChad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductChad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductChadIdentifier(product: Pick<IProductChad, 'id'>): number {
    return product.id;
  }

  compareProductChad(o1: Pick<IProductChad, 'id'> | null, o2: Pick<IProductChad, 'id'> | null): boolean {
    return o1 && o2 ? this.getProductChadIdentifier(o1) === this.getProductChadIdentifier(o2) : o1 === o2;
  }

  addProductChadToCollectionIfMissing<Type extends Pick<IProductChad, 'id'>>(
    productCollection: Type[],
    ...productsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const products: Type[] = productsToCheck.filter(isPresent);
    if (products.length > 0) {
      const productCollectionIdentifiers = productCollection.map(productItem => this.getProductChadIdentifier(productItem)!);
      const productsToAdd = products.filter(productItem => {
        const productIdentifier = this.getProductChadIdentifier(productItem);
        if (productCollectionIdentifiers.includes(productIdentifier)) {
          return false;
        }
        productCollectionIdentifiers.push(productIdentifier);
        return true;
      });
      return [...productsToAdd, ...productCollection];
    }
    return productCollection;
  }
}
